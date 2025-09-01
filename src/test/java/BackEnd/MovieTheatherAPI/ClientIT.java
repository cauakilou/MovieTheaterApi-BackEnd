package BackEnd.MovieTheatherAPI;


import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientCreateDto;
import BackEnd.MovieTheatherAPI.Model.Dto.Client.ClientResponseDto;
import BackEnd.MovieTheatherAPI.Model.Dto.PageableDto;
import BackEnd.MovieTheatherAPI.Model.Exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    @Sql(scripts = "/sql/clientes/Clients-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clientes/Clients-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    class ClientIT {

        @Autowired
        WebTestClient testClient;

        //Testes para a criação de clientes
        @Test
        void CriarClient_dadosValidos_status201() {
            ClientResponseDto responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("tobias ferreira", "78909807016", "21987654321"))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(ClientResponseDto.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("tobias ferreira");
            org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("78909807016");
            org.assertj.core.api.Assertions.assertThat(responseBody.getPhoneNumber()).isEqualTo("21987654321");
        }

        @Test
        void CriarClient_cpfRepetido_status409() {
            ErrorMessage responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("tobias ferreira", "81708991093", "21987654321"))
                    .exchange()
                    .expectStatus().isEqualTo(409)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
        }

        @Test
        void CriarClient_dadosInvalidos_status422() {
            ErrorMessage responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("", "97688468019", "213534321"))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

            responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("tobias ferreira", "", "213534321"))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

            responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("", "", ""))
                    .exchange()
                    .expectStatus().isEqualTo(422)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        }

        @Test
        void CriarClient_RoleAdmin_status403() {
            ErrorMessage responseBody = testClient
                    .post()
                    .uri("/api/v1/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .bodyValue(new ClientCreateDto("tobias ferreira", "97688468019", "1140028922"))
                    .exchange()
                    .expectStatus().isForbidden()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        }

        // recuperar cliente pelo ID

        @Test
        void RecuperarPeloIdClient_dadosValidos_status200() {
            ClientResponseDto responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes/10")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(ClientResponseDto.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Bianca Silva");
            org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("97688468019");
        }

        @Test
        void RecuperarPeloIdClient_RoleClient_status403() {
            ErrorMessage responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes/10")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
                    .exchange()
                    .expectStatus().isForbidden()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

        @Test
        void RecuperarPeloIdClient_IdInvalido_status404() {
            ErrorMessage responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes/11")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

        }

        //Testes para recuperar todos os clientes

        @Test
        void RecuperarClients_dadosValidos_status200() {
            PageableDto responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(PageableDto.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getTotalElements()).isEqualTo(2);
            org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isZero();
            org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

            responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes?size=1&page=1")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(PageableDto.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).hasSize(1);
            org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
            org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        }

        @Test
        void RecuperarClients_RoleInvalida_status403() {
            ErrorMessage responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                    .exchange()
                    .expectStatus().isEqualTo(403)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        }

        //Testes para Recuperar os dados do cliente

        @Test
        void RecuperarClientDetalhes_dadosValidos_status200() {
            ClientResponseDto responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes/detalhes")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "caua@email.com", "654321"))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(ClientResponseDto.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Bianca Silva");
            org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("97688468019");
        }

        @Test
        void RecuperarClientDetalhes_Admin_status403() {
            ErrorMessage responseBody = testClient
                    .get()
                    .uri("/api/v1/clientes/detalhes")
                    .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                    .exchange()
                    .expectStatus().isEqualTo(403)
                    .expectBody(ErrorMessage.class)
                    .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

    }
