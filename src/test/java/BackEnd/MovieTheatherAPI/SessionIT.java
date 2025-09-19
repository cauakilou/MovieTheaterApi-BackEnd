package BackEnd.MovieTheatherAPI;

import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.SessionResponseDto;
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
@Sql(scripts = "/Sql/Sessions/Session-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/Sql/Sessions/Session-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SessionIT{

    @Autowired
    WebTestClient testClient;

    // =====================================================================================
    // ==                             TESTES PARA POST /api/v1/sessoes                      ==
    // =====================================================================================

    @Test
    @DisplayName("POST /sessoes - Sucesso: Deve criar sessão com dados válidos e retornar status 201")
    void criarSessao_ComDadosValidos_RetornarStatus201() {
        SessionResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/sessoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new SessionCreateDto("2025-12-25", "18:00", 10L, 10L))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SessionResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getData()).isEqualTo("2025-12-25");
        assertThat(responseBody.getInicio()).isEqualTo("18:00");
        assertThat(responseBody.getFilme().getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("POST /sessoes - Falha: Deve retornar status 422 para dados inválidos")
    void criarSessao_ComDadosInvalidos_RetornarStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/sessoes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new SessionCreateDto("data-invalida", "hora-invalida", 0L, 0L))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    // =====================================================================================
    // ==                          TESTES PARA GET /api/v1/sessoes/{id}                     ==
    // =====================================================================================

    @Test
    @DisplayName("GET /sessoes/{id} - Sucesso: Deve buscar sessão por ID e retornar status 200")
    void buscarSessaoPorId_ComIdExistente_RetornarStatus200() {
        SessionResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SessionResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isEqualTo(100);
        assertThat(responseBody.getFilme().getNome()).isEqualTo("O Exterminador do Futuro 2");
    }

    @Test
    @DisplayName("GET /sessoes/{id} - Falha: Deve retornar status 404 para ID inexistente")
    void buscarSessaoPorId_ComIdInexistente_RetornarStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/sessoes/999")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    // =====================================================================================
    // ==                            TESTES PARA GET /api/v1/sessoes                        ==
    // =====================================================================================

    @Test
    @DisplayName("GET /sessoes - Sucesso: Deve retornar todas as sessões paginadas")
    void buscarTodasSessoes_SemFiltros_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(3);
        assertThat(responseBody.getContent()).hasSize(3);
    }

    @Test
    @DisplayName("GET /sessoes - Filtro: Deve retornar sessões filtradas por ID do filme")
    void buscarTodasSessoes_FiltroPorMovieId_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes?movieId=90") // Filtro por filme 'Parasita'
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(1);
        assertThat(responseBody.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("GET /sessoes - Filtro: Deve retornar sessões filtradas por gênero")
    void buscarTodasSessoes_FiltroPorGenero_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes?genre=SUSPENSE") // Filtro por gênero
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(1);
        assertThat(responseBody.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("GET /sessoes - Filtro: Deve retornar sessões filtradas por data")
    void buscarTodasSessoes_FiltroPorData_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes?date=2025-10-21") // Filtro por data
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(1);
        assertThat(responseBody.getContent()).hasSize(1);
    }

    @Test
    @DisplayName("GET /sessoes - Filtro: Deve retornar sessões com múltiplos filtros")
    void buscarTodasSessoes_FiltroCombinado_RetornarStatus200() {
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/sessoes?movieId=10&date=2025-10-20") // Filtro por filme e data
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTotalElements()).isEqualTo(1);
        assertThat(responseBody.getContent()).hasSize(1);
    }

    // =====================================================================================
    // ==                         TESTES PARA DELETE /api/v1/sessoes/{id}                   ==
    // =====================================================================================

    @Test
    @DisplayName("DELETE /sessoes/{id} - Sucesso: Deve deletar sessão com ID existente e retornar status 204")
    void deletarSessao_ComIdExistenteEAdmin_RetornarStatus204() {
        testClient
                .delete()
                .uri("/api/v1/sessoes/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("DELETE /sessoes/{id} - Falha: Deve retornar status 404 para ID inexistente")
    void deletarSessao_ComIdInexistente_RetornarStatus404() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/sessoes/999")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    @DisplayName("DELETE /sessoes/{id} - Falha: Deve retornar status 403 para usuário com role CLIENT")
    void deletarSessao_ComUsuarioCliente_RetornarStatus403() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/sessoes/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }
}