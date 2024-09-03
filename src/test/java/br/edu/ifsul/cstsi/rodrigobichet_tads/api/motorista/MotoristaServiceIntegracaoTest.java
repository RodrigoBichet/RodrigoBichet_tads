package br.edu.ifsul.cstsi.rodrigobichet_tads.api.motorista;

import br.edu.ifsul.cstsi.rodrigobichet_tads.RodrigoBichetTadsApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; //jupiter indica que é JUnit 5
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/*
    Realiza o teste de integração da unidade MotoristaService.

    Exitem dois padrões amplamente aceitos para escrever testes: Triple A e GWT.
    O padrão GWT (Given-When-Then, em português Dado-Quando-Então. Dado um conjunto de entradas - Quando a aplicação
    faz uma ação X - Então é esperado Y) é aplicado em BDD (Behavior-Driven Development, Desenvolvimento Orientado ao Comportamento).

    ### Arrange, Act, Assert (AAA)
    O padrão AAA é amplamente utilizado e consiste em três etapas distintas:
    a) Arrange (Preparar): Nesta etapa, são realizadas todas as configurações iniciais necessárias para que o
    cenário de teste possa ser executado. Isso pode incluir a criação de objetos, definição de variáveis,
    configuração de ambiente e qualquer outra preparação necessária para que o teste seja executado em um estado
    específico.
    b) Act (Ação): Nesta fase, a ação que se deseja testar é executada. Pode ser a chamada de um método, uma
    interação com a interface do usuário ou qualquer outra operação que seja o foco do teste.
    c) Assert (Afirmar): Na última etapa, os resultados são verificados em relação ao comportamento esperado. É
    onde se avalia se o resultado obtido após a ação está de acordo com o que se esperava do teste. Caso haja alguma
    discrepância entre o resultado real e o esperado, o teste falhará.

    Pequeno Glossário:
    Às vezes a sopa de letrinhas utilizadas na área de desenvolvimento de software pode causar alguma confusão. Para
    esclarecer um pouco mais, segue alguns conceitos das metodologias ágeis:

    + TLD (Test-Last Development): É o Desenvolvimento com Testes Posteriores. Desenvolve-se o software, depois o testa
        para garantir sua qualidade.

    + TDD (Test Driven Development): É o Desenvolvimento Orientado a Testes. Primeiro, cria-se o teste conforme o resultado
        que se deseja atingir para determinada funcionalidade. Depois, se aprimora o código (com refinamentos sussecivos).
        Geralmente é o próprio desenvolvedor que escreve os testes.

    + BDD (Behavior Driven Development): é o Desenvolvimento Orientado ao Comportamento, cujos testes são baseados no
        comportamento do software ao longo da sua vida útil. Com o BDD, a principal diferença é que as equipes de QA
        escrevem os testes, necessários para passar, antes que o desenvolvimento possa marcar um recurso como concluído.

    + DDD (Domain-Driven Design): Design Orientado pelo Domínio é uma abordagem para o desenvolvimento de software que
        enfatiza a importância de entender profundamente o domínio do problema que está sendo resolvido. Utilizada para
        desenvolvimento de softwares complexos, como os empresariais.

    Ao analisar as metodologias descritas acima, considere que:
        As empresas têm a necessidade de procurar e adotar técnicas e abordagens para o processo de desenvolvimento
        de software, a fim de melhorarem as métricas de qualidade, reduzir a taxa de não cumprimento, aumentarem a
        produtividade das equipes e, consequentemente, produzir software com qualidade.
 */

@SpringBootTest (classes = RodrigoBichetTadsApplication.class) //carrega o Context do app em um container Spring Boot, sem um servidor web, mas com JPA e acesso a banco de dados
@ActiveProfiles("test") //indica o profile que o Spring Boot deve utilizar para passar os testes
public class MotoristaServiceIntegracaoTest {

    @Autowired //se carregou o Context do app é possível injetar qualquer Bean do projeto, como Services
    private MotoristaService service;

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    @DisplayName("Busca os motoristas na base de dados, espera 5 objetos")
    public void testGetMotoristasEsperaUmaPaginaCom5Objetos() { //O nome do método de teste é importante porque deve transmitir a essência do que ele verifica. Este não é um requisito técnico, mas sim uma oportunidade de capturar informações.
        // ARRANGE
        var pageable = PageRequest.of(0, 50);

        // ACT
        var motoristas = service.getMotoristas(pageable);

        // ASSERT
        assertEquals(5, motoristas.getContent().size());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testGetMotoristaByIdEsperaOMotoristaDeId1(){
        // ARRANGE + ACT
        var p = service.getMotoristaById(1L);

        // ASSERT
        assertNotNull(p);
        assertEquals("Fausto Silva", p.get().getNome());
        assertEquals("faustosilva@gmail.com", p.get().getEmail());
        assertEquals("123456789", p.get().getTelefone());


    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testGetMotoristasByNomeEsperaUmObjetoPorNomePesquisado(){
        // ARRANGE + ACT + ASSERT
        assertEquals(1, service.getMotoristasByNome("Fausto Silva").size());
        assertEquals(1, service.getMotoristasByNome("Silvio Santos").size());
        assertEquals(1, service.getMotoristasByNome("Gugu").size());
        assertEquals(1, service.getMotoristasByNome("Raul Gil").size());
        assertEquals(1, service.getMotoristasByNome("Rodrigo Faro").size());
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testInsertEsperaOObjetoInseridoEoDeleta() {
        // ARRANGE
        var motorista = new Motorista();
        motorista.setNome("Eliana");
        motorista.setEmail("eliana@gmail.com");
        motorista.setTelefone("999999999");


        // ACT
        var m = service.insert(motorista);

        // ASSERT
        assertNotNull(m);
        Long id = m.getId();
        assertNotNull(id);
        m = service.getMotoristaById(id).get();
        assertNotNull(m); //confirma se o motorista foi realmente inserido na base de dados
        //compara os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Eliana", m.getNome());
        assertEquals("eliana@gmail.com", m.getEmail());
        assertEquals("999999999", m.getTelefone());

        //Deleta o objeto (pois está trabalhando com um banco de dados real e, por isso, requer que seja mantida sua consistência para os demais testes)
        service.delete(id);
        //Verifica se deletou
        if(service.getMotoristaById(id).isPresent()){
            fail("O motorista não foi excluído");
        }
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testUpdateEsperaOObjetoAlteradoERetornaAoValorOriginal(){
        // ARRANGE
        var pOriginal = service.getMotoristaById(1L).get(); //motorista original na base de dados
        var motoristaMock = new Motorista();
        motoristaMock.setId(pOriginal.getId());
        motoristaMock.setNome("Celso Portioli");
        motoristaMock.setEmail("celsoportioli@gmail.com");
        motoristaMock.setTelefone("111111111");

        // ACT
        var motoristaAlterado = service.update(motoristaMock);

        // ASSERT
        assertNotNull(motoristaAlterado);
        assertEquals("Celso Portioli", motoristaAlterado.getNome());
        assertEquals("celsoportioli@gmail.com", motoristaAlterado.getEmail());
        assertEquals("111111111", motoristaAlterado.getTelefone());


        //volta ao valor original (para manter a consistência do banco de dados)
        var motoristaOriginal = service.update(pOriginal);
        assertNotNull(motoristaOriginal);
    }

    @Test //Esta anotação JUnit sinaliza que este método é um caso de teste
    public void testDeleteEsperaAExclusaoDeUmObjetoInserido(){
        // ARRANGE
        var motorista = new Motorista();
        motorista.setNome("Datena");
        motorista.setEmail("datena@gmail.com");
        motorista.setTelefone("666666666");
        var m = service.insert(motorista);
        assertNotNull(m);
        //confirma se o motorista foi realmente inserido na base de dados
        Long id = m.getId();
        assertNotNull(id);
        m = service.getMotoristaById(id).get();
        assertNotNull(m);

        // ACT
        service.delete(id);

        // ASSERT
        if(service.getMotoristaById(id).isPresent()){
            fail("O motorista não foi excluído");
        }
    }
}