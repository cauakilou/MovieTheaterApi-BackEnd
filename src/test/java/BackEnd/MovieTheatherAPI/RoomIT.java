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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/Sql/Room/Room-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/Sql/Room/Room-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RoomIT {

    @Autowired
    WebTestClient testClient;

    // =====================================================================================
    // ==                             TESTES PARA POST /api/v1/salas                       ==
    // =====================================================================================

    @Test
    @DisplayName("POST /salas - Sucesso: Deve criar sala com dados válidos e retornar status 201")
     void criarSala_ComDadosValidos_RetornarStatus201() {
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
        assertThat(responseBody.getSeats()).hasSize(50);
    }

    @Test
    @DisplayName("POST /salas - Falha: Deve retornar status 422 para dados inválidos")
     void criarSala_ComDadosInvalidos_RetornarStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new RoomCreateDto("NOME_INVALIDO", "TIPO_INVALIDO", 0, 0))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
        assertThat(responseBody.getErrors()).containsKeys("nome", "tipo");
    }

    @Test
    @DisplayName("POST /salas - Falha: Deve retornar status 409 para nome de sala duplicado")
     void criarSala_ComNomeDuplicado_RetornarStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new RoomCreateDto("sala-01", "PADRAO", 5, 5)) // 'sala-01' já existe
                .exchange()
                .expectStatus().isEqualTo(409) // Conflict
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    @DisplayName("POST /salas - Falha: Deve retornar status 403 para usuário com role CLIENT")
     void criarSala_ComUsuarioCliente_RetornarStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .bodyValue(new RoomCreateDto("sala-05", "IMAX", 10, 10))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("POST /salas - Falha: Deve retornar status 401 para usuário não autenticado")
     void criarSala_SemAutenticacao_RetornarStatus401() {
        testClient
                .post()
                .uri("/api/v1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new RoomCreateDto("sala-99", "PADRAO", 5, 5))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    // =====================================================================================
    // ==                          TESTES PARA GET /api/v1/salas/{id}                     ==
    // =====================================================================================

    @Test
    @DisplayName("GET /salas/{id} - Sucesso: Deve buscar sala por ID como ADMIN e retornar status 200")
     void buscarSalaPorId_ComIdExistenteEAdmin_RetornarStatus200() {
        RoomResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/salas/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoomResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getNome()).isEqualTo("sala-01");
        assertThat(responseBody.getSeats()).hasSize(40);
    }

    @Test
    @DisplayName("GET /salas/{id} - Falha: Deve retornar status 404 para ID inexistente")
    void buscarSalaPorId_ComIdInexistente_RetornarStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/salas/99")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("GET /salas/{id} - Falha: Deve retornar status 403 para usuário com role CLIENT")
     void buscarSalaPorId_ComUsuarioCliente_RetornarStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/salas/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("GET /salas/{id} - Falha: Deve retornar status 401 para usuário não autenticado")
     void buscarSalaPorId_SemAutenticacao_RetornarStatus401() {
        testClient
                .get()
                .uri("/api/v1/salas/10")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    // =====================================================================================
    // ==                            TESTES PARA GET /api/v1/salas                        ==
    // =====================================================================================

    @Test
    @DisplayName("GET /salas - Sucesso: Deve buscar todas as salas como ADMIN e retornar status 200")
     void buscarTodasSalas_ComoAdmin_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/salas?size=2&page=0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(3);
        assertThat(responseBody.getTotalPages()).isEqualTo(2);
        assertThat(responseBody.getContent()).hasSize(2);
    }

    @Test
    @DisplayName("GET /salas - Falha: Deve retornar status 403 para usuário com role CLIENT")
     void buscarTodasSalas_ComoCliente_RetornarStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/salas")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("GET /salas - Falha: Deve retornar status 401 para usuário não autenticado")
     void buscarTodasSalas_SemAutenticacao_RetornarStatus401() {
        testClient
                .get()
                .uri("/api/v1/salas")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    // =====================================================================================
    // ==                         TESTES PARA DELETE /api/v1/salas/{id}                   ==
    // =====================================================================================

    @Test
    @DisplayName("DELETE /salas/{id} - Sucesso: Deve deletar sala com ID existente e retornar status 204")
     void deletarSala_ComIdExistenteEAdmin_RetornarStatus204() {
        testClient
                .delete()
                .uri("/api/v1/salas/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("DELETE /salas/{id} - Falha: Deve retornar status 404 para ID inexistente")
     void deletarSala_ComIdInexistente_RetornarStatus404() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/salas/99")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("DELETE /salas/{id} - Falha: Deve retornar status 403 para usuário com role CLIENT")
     void deletarSala_ComUsuarioCliente_RetornarStatus403() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/salas/10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    @DisplayName("DELETE /salas/{id} - Falha: Deve retornar status 401 para usuário não autenticado")
     void deletarSala_SemAutenticacao_RetornarStatus401() {
        testClient
                .delete()
                .uri("/api/v1/salas/10")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}