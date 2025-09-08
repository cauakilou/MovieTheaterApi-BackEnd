package BackEnd.MovieTheatherAPI;


import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Room.RoomResponseDto;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/Room/Room-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/Room/Room-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoomIT {
    @Autowired
    WebTestClient testClient;

        // --- TESTE 1: SUCESSO (201 CREATED) ---
        @Test
        public void criarsala_ComDadosValidos_RetornarStatus201() {
            RoomResponseDto responseBody = testClient
                    .post()
                    .uri("/api/v1/salas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .bodyValue(new RoomCreateDto("sala-04", "VIP", 5, 10))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(RoomResponseDto.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody.getNome()).isEqualTo("sala-04");
            assertThat(responseBody.getTipo()).isEqualTo("VIP");
            assertThat(responseBody.getSeats()).hasSize(50); // 5 linhas * 10 colunas
        }

        // --- TESTE 2: DADOS INVÁLIDOS (422 UNPROCESSABLE ENTITY) ---
        @Test
        public void criarsala_ComDadosInvalidos_RetornarStatus422() {
            ErrorMessage responseBody = testClient
                    .post()
                    .uri("/api/v1/salas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .bodyValue(new RoomCreateDto("", "TIPO_INVALIDO", 0, 0))
                    .exchange()
                    .expectStatus().isEqualTo(422) // Unprocessable Entity
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody.getStatus()).isEqualTo(422);
            assertThat(responseBody.getErrors()).containsKeys("nome", "tipo");
        }

        // --- TESTE 3: NÃO AUTENTICADO (401 UNAUTHORIZED) ---
        @Test
        public void criarsala_SemAutenticacao_RetornarStatus401() {
            testClient
                    .post()
                    .uri("/api/v1/salas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new RoomCreateDto("sala Fantasma", "PADRAO", 5, 5))
                    .exchange()
                    .expectStatus().isUnauthorized(); // Unauthorized
        }

        // --- TESTE 4: SEM PERMISSÃO (403 FORBIDDEN) ---
        @Test
        public void criarsala_ComUsuarioCliente_RetornarStatus403() {
            ErrorMessage responseBody = testClient
                    .post()
                    .uri("/api/v1/salas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                    .bodyValue(new RoomCreateDto("sala-99", "IMAX", 10, 10))
                    .exchange()
                    .expectStatus().isForbidden() // Forbidden
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody.getStatus()).isEqualTo(403);
        }

        //buscar todos as salas

    @Test
    void buscarTodosFilmes_comoAdmin_retornaListaPaginada_Status200() {
        // NOTA: Este teste assume que seu script Movie-insert.sql insere alguns filmes.
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/salas?size=1&page=0") // Testando com parâmetros de paginação
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk() // Espera 200 OK
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).hasSize(1); // Verifica o tamanho da página
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero(); // Verifica a página atual
    }

    @Test
    void buscarTodosFilmes_comoClient_retornaAcessoNegado_Status403() {
        testClient
                .get()
                .uri("/api/v1/salas")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321")) // Autenticado como CLIENT
                .exchange()
                .expectStatus().isForbidden(); // Espera 403 Forbidden
    }


    }



