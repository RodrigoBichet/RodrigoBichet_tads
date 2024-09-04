package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import br.edu.ifsul.cstsi.rodrigobichet_tads.api.BaseAPIIntegracaoTest;
import br.edu.ifsul.cstsi.rodrigobichet_tads.api.CustomPageImpl;
import br.edu.ifsul.cstsi.rodrigobichet_tads.RodrigoBichetTadsApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; //jupiter indica que é JUnit 5
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
    Realiza o teste de integração da unidade MotoristaController.
    Utiliza como dependência principal a classe TestRestTemplate (do Spring), implementada na superclasse BaseAPIIntegracaoTest.
 */

@SpringBootTest(classes = RodrigoBichetTadsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //carrega o Context do app em um container Spring Boot com um servidor web
@ActiveProfiles("test") //indica o profile que o Spring Boot deve utilizar para passar os testes
public class MotoristaControllerIntegracaoTest extends BaseAPIIntegracaoTest {

    //Métodos utilitários (eles encapsulam o TestRestTemplate e eliminam a repetição de código nos casos de teste)
    private ResponseEntity<MotoristaDTOResponse> getMotorista(String url) {
        return get(url, MotoristaDTOResponse.class);
    }

    private ResponseEntity<CustomPageImpl<MotoristaDTOResponse>> getMotoristasPageble(String url) {
        var headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});
    }

    private ResponseEntity<List<MotoristaDTOResponse>> getMotoristasList(String url) {
        var headers = getHeaders();

        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    @DisplayName("Espera uma página, testa se tem 5 objetos, busca por página, de tamanho 5, e testa se tem 5 objetos")
    public void selectAllEsperaUmaPaginaCom5ObjetosEUmaPaginaDe5Objetos() { //O nome do método de teste é importante porque deve transmitir a essência do que ele verifica. Este não é um requisito técnico, mas sim uma oportunidade de capturar informações.
        // ACT
        var page = getMotoristasPageble("/api/v1/motoristas").getBody();

        // ASSERT (testa se retorna a quantidade de dados esperada)
        assertNotNull(page);
        assertEquals(5, page.stream().count());

        // ACT - testa uma requisição com os parametrôs page e size
        page = getMotoristasPageble("/api/v1/motoristas?page=0&size=5").getBody();

        // ASSERT (testa se retorna o tamanho de página solicitado)
        assertNotNull(page);
        assertEquals(5, page.stream().count());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void selectByNomeEsperaUmObjetoPorNomePesquisado() {
        // ACT + ASSERT
        assertEquals(1, getMotoristasList("/api/v1/motoristas/nome/Fausto").getBody().size());
        assertEquals(1, getMotoristasList("/api/v1/motoristas/nome/Silvio").getBody().size());
        assertEquals(1, getMotoristasList("/api/v1/motoristas/nome/Gugu").getBody().size());
        assertEquals(1, getMotoristasList("/api/v1/motoristas/nome/Raul").getBody().size());
        assertEquals(1, getMotoristasList("/api/v1/motoristas/nome/Rodrigo").getBody().size());

        // ACT + ASSERT
        assertEquals(HttpStatus.NO_CONTENT, getMotoristasList("/api/v1/motoristas/nome/xxx").getStatusCode());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void selectByIdEsperaUmObjetoPorIdPesquisadoENotFoudParaIdInexistente() {
        // ACT + ASSERT
        assertNotNull(getMotorista("/api/v1/motoristas/1"));
        assertNotNull(getMotorista("/api/v1/motoristas/2"));
        assertNotNull(getMotorista("/api/v1/motoristas/3"));
        assertNotNull(getMotorista("/api/v1/motoristas/4"));
        assertNotNull(getMotorista("/api/v1/motoristas/5"));
        assertEquals(HttpStatus.NOT_FOUND, getMotorista("/api/v1/motoristas/100000").getStatusCode());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testInsertEspera204CreatedE404ENotFound() {
        // ARRANGE
        var MotoristaDTOPost = new MotoristaDTOPost(
                "Eliana",
                "eliana@gmail.com",
                "999999999"
        );

        // ACT
        var response = post("/api/v1/motoristas", MotoristaDTOPost, null);

        // ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        var location = response.getHeaders().get("location").get(0);
        var m = getMotorista(location).getBody();
        assertNotNull(m);
        assertEquals("Eliana", m.nome());
        assertEquals("eliana@gmail.com", m.email());
        assertEquals("999999999", m.telefone());


        // ACT
        delete(location, null);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, getMotorista(location).getStatusCode());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testUpdateEspera200OkE404ENotFound() {
        // ARRANGE
        var MotoristaDTOPost = new MotoristaDTOPost(
                "Celso",
                "celso@gmail.com",
                "111111111"
        );

        var responsePost = post("/api/v1/motoristas", MotoristaDTOPost, null);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        var location = responsePost.getHeaders().get("location").get(0);
        var mDto = getMotorista(location).getBody();
        assertNotNull(mDto);
        assertEquals("Celso", mDto.nome());
        assertEquals("celso@gmail.com", mDto.email());
        assertEquals("111111111", mDto.telefone());

        //prepara um DTO para o PUT
        var motoristaDTOPut = new MotoristaDTOPut(
                "Portioli",
                "portioli@gmail.com",
                "222222222"
        );

        // ACT
        var responsePUT = put(location, motoristaDTOPut, MotoristaDTOResponse.class);

        // ASSERT
        assertEquals(HttpStatus.OK, responsePUT.getStatusCode());
        assertEquals("Portioli", responsePUT.getBody().nome());
        assertEquals("portioli@gmail.com", responsePUT.getBody().email());
        assertEquals("222222222", responsePUT.getBody().telefone());


        // ACT
        delete(location, null);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, getMotorista(location).getStatusCode());

    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testDeleteEspera200OkE404NotFound() {
        // ARRANGE
        var motorista = new Motorista();
        motorista.setNome("Datena");
        motorista.setEmail("datena@gmail.com");
        motorista.setTelefone("666666666");

        var responsePost = post("/api/v1/motoristas", motorista, null);
        assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
        var location = responsePost.getHeaders().get("location").get(0);
        var m = getMotorista(location).getBody();
        assertNotNull(m);
        assertEquals("Datena", m.nome());

        // ACT
        var responseDelete = delete(location, null);

        // ASSERT
        assertEquals(HttpStatus.OK, responseDelete.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getMotorista(location).getStatusCode());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testGetNotFoundEspera404NotFound() {
        // ARRANGE + ACT
        var response = getMotorista("/api/v1/motoristas/1100");

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}