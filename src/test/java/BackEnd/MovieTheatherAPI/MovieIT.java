package BackEnd.MovieTheatherAPI;

import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Session.Movie.MovieResponseDto;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.Classificacao.QUATORZE_ANOS;
import static BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.Genero.ACAO;
import static BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.Status.EM_BREVE;
import static BackEnd.MovieTheatherAPI.Model.Entity.MovieEntity.Status.EM_CARTAZ;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/Sql/Movie/Movie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/Sql/Movie/Movie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MovieIT {

    @Autowired
    WebTestClient testClient;

    //Testes para a criação de clientes
    @Test
    void CriarFilme_comDadosValidos_status201() {
        MovieResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new MovieCreateDto(
    "Vingadores: Ultimato",
    "Uma equipe de heróis se une para reverter as ações de Thanos.",
    "https://image.tmdb.org/t/p/original/q6725aR8Zs4IwGMXzZT8aC8j4bV.jpg", // URL do pôster adicionada
    "QUATORZE_ANOS",
    "ACAO",
    181
))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MovieResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Vingadores: Ultimato");
        org.assertj.core.api.Assertions.assertThat(responseBody.getSinopse()).isEqualTo("Uma equipe de heróis se une para reverter as ações de Thanos.");
        org.assertj.core.api.Assertions.assertThat(responseBody.getClassificacao()).isEqualTo(QUATORZE_ANOS);
        org.assertj.core.api.Assertions.assertThat(responseBody.getGenero()).isEqualTo(ACAO);
        org.assertj.core.api.Assertions.assertThat(responseBody.getDuracaoFormatada()).isEqualTo("3:01");
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(EM_BREVE);
    }


    @Test
    void CriarFilme_ComDadosInvalidos_Status422() {
        // Tenta criar um filme com o nome em branco, esperando um erro de validação.
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new MovieCreateDto("", "Sinopse válida.","a", "LIVRE", "ACAO", 120))
                .exchange()
                .expectStatus().isEqualTo(422) // Espera o status 422
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        // Tenta criar um filme com duração inválida (zero), esperando um erro de validação.
        responseBody = testClient
                .post()
                .uri("/api/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new MovieCreateDto("Nome Válido", "Sinopse válida.","a", "LIVRE", "ACAO", 0))
                .exchange()
                .expectStatus().isEqualTo(422) // Espera o status 422
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    void CriarFilme_ComDadosRepetidos_Status409() {
        // NOTA: Este teste assume que o filme "O Poderoso Chefão"
        // já foi inserido pelo seu script /Sql/Movie/Movie-insert.sql
        // Se o nome for outro, apenas ajuste o DTO abaixo.
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new MovieCreateDto("Parasita", "A saga de uma família mafiosa.","a","DEZESSEIS_ANOS", "DRAMA", 175))
                .exchange()
                .expectStatus().isEqualTo(409) // Espera o status 409
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).contains("já cadastrado");
    }

    @Test
    void CriarFilme_comoClient_Status403() {
        // NOTA: Este teste assume que existe um usuário "bia@email.com" com a role "CLIENT"
        // no seu banco de dados de teste para que a autenticação funcione.
        testClient
                .post()
                .uri("/api/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com","654321")) // Autenticando como CLIENT
                .bodyValue(new MovieCreateDto("Filme qualquer", "Sinopse qualquer.","a", "LIVRE", "COMEDIA", 90))
                .exchange()
                .expectStatus().isForbidden(); // Espera o status 403
    }

    // =====================================================================================
// ==                            TESTES PARA GET /api/v1/filmes                       ==
// =====================================================================================

    @Test
    void buscarTodosFilmes_comoAdmin_retornaListaPaginada_Status200() {
        // NOTA: Este teste assume que seu script Movie-insert.sql insere alguns filmes.
        PageableDto responseBody = testClient
                .get()
                .uri("/api/v1/filmes?size=3&page=0") // Testando com parâmetros de paginação
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk() // Espera 200 OK
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).hasSize(3); // Verifica o tamanho da página
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero(); // Verifica a página atual
    }

    @Test
    void buscarTodosFilmes_comoClient_retornaAcessoNegado_Status403() {
        testClient
                .get()
                .uri("/api/v1/filmes")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321")) // Autenticado como CLIENT
                .exchange()
                .expectStatus().isForbidden(); // Espera 403 Forbidden
    }

// =====================================================================================
// ==                TESTES PARA PATCH /api/v1/filmes/{id}/status/{status}            ==
// =====================================================================================

    @Test
    void mudarStatusDoFilme_comoAdmin_comDadosValidos_retornaStatusAlterado_Status200() {
        // NOTA: Este teste assume que existe um filme com ID 100 no seu DB de teste.
        // Ajuste o ID conforme necessário.
        long idFilmeExistente = 100;

        MovieResponseDto responseBody = testClient
                .patch()
                .uri("/api/v1/filmes/" + idFilmeExistente + "/status/EM_CARTAZ")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk() // Espera 200 OK
                .expectBody(MovieResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(idFilmeExistente);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(EM_CARTAZ);
    }

    @Test
    void mudarStatusDoFilme_comIdInexistente_retornaNotFound_Status404() {
        long idFilmeInexistente = 999;
        testClient
                .patch()
                .uri("/api/v1/filmes/" + idFilmeInexistente + "/status/EM_CARTAZ")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound(); // Espera 404 Not Found
    }

// =====================================================================================
// ==                       TESTES PARA DELETE /api/v1/filmes/{id}                    ==
// =====================================================================================

    @Test
    void deletarFilme_comoAdmin_comIdValido_retornaOk_Status200() {
        // NOTA: Este teste assume que existe um filme com ID 101 no seu DB de teste.
        // Use um ID diferente do teste de PATCH para evitar conflitos.
        long idFilmeExistente = 100;

        testClient
                .delete()
                .uri("/api/v1/filmes/" + idFilmeExistente)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk(); // Espera 200 OK (conforme sua implementação)
    }

    @Test
    void deletarFilme_comoClient_retornaAcessoNegado_Status403() {
        long idFilmeExistente = 100;
        testClient
                .delete()
                .uri("/api/v1/filmes/" + idFilmeExistente)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321")) // Autenticado como CLIENT
                .exchange()
                .expectStatus().isForbidden(); // Espera 403 Forbidden
    }

    @Test
    void deletarFilme_comIdInexistente_retornaNotFound_Status404() {
        long idFilmeInexistente = 999;
        testClient
                .delete()
                .uri("/api/v1/filmes/" + idFilmeInexistente)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound(); // Espera 404 Not Found
    }

}
