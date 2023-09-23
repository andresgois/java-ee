# JAVA EE

### Maven: Build do zero a web
- [Do zero aos relatórios](#anc1)
- [Maven no Eclipse](#anc2)
- [Repositório remoto e local](#anc3)
- [Relatórios de qualidade e cobertura](#anc4)
- [Criando um projeto web](#anc5)
- [Projeto web](#anc6)

<a name="anc1"></a>

## Do zero aos relatórios
- Compilar classe calculadora, passando para a pasta target
```
javac -sourcepath src -d target src/Calculadora.java 
```

- [Baixar Maven](https://maven.apache.org/)
- Ajuda `./mvn --help`
- No diretório original queremos editar um arquivo para configurar que o diretório /Users/alura/Documents/guilherme/workspace/apache-maven-3.3.9/bin irá para o path global de execução de arquivos. **No Linux**, fazemos isso por meio da variável de ambiente que podemos configurar diretamente neste diretório, utilizando o comando vi .bash_profile.
- Estamos prontos para editar o arquivo, portanto pressionaremos a tecla "A" para realizar a inserção de conteúdo. Escreveremos o comando export para exportar a configuração de PATH como sendo o atual ($PATH), e (:) o diretório :/Users/alura/Documents/guilherme/workspace/apache-maven-3.3.9/bin. Estamos afirmando que o novo path é composto pelo atual, e pelo novo diretório.
```
export PATH=$PATH:/Users/alura/Documents/guilherme/workspace/apache-maven-3.3.9/bin
```
- Solicitaremos que o Maven gere um novo projeto
- ID do nosso projeto
- Pacote básico da empresa
- Desabilitaremos o método interativo usando as informações padrão para configurarmos nosso projeto. Para isso, incluiremos -DinteractiveMode=false

```
mvn archetype:generate -DartifactId=produtos -DgroupId=br.com.alura.maven -DinteractiveMode=false -DarchetypeArtifactId=maven-archetype-quickstart
```
- Após pressionarmos o botão "Enter", será feito o download de várias informações. 

- Compilação manualmente
    - mvn compile
- Para compilarmos a classe de teste, basta usarmos `mvn test` no terminal. 
- Executamos o mvn clean em nosso terminal, o que faz com que todo o código de saída seja limpo, e fiquemos somente com o de fonte. Isso faz com que o diretório target seja removido
- Por exemplo, queremos gerar um relatório dos testes realizados. Por padrão, quando executamos mvn test, é gerado um arquivo TXT e uma versão XML, e ambos os formatos são desagradáveis para a leitura. Seria muito mais interessante que o relatório fosse exibido no navegador, e para isso utilizamos` mvn report`.
- Alguns comandos, como clean, test ou compile são de plugins padrão do Maven, e a instalação é realizada automaticamente. Alguns dos objetivos que queremos alcançar, como o report, não fazem parte dos plugins padrão, portanto o Maven não encontrará esse recurso de forma automática, sendo necessário declarar qual estamos utilizando.
```
mvn surefire-report:report
```

- Será realizado o download do que for necessário, e o relatório será gerado. Entraremos no diretório "produtos > target > site > surefire-report.html", e desse modo o teste será exibido no navegador.

- Para descobrirmos o nome de todos esses plugins, digitaremos no buscador do Google algo como "maven plugins", e várias informações serão disponibilizadas. Caso seja necessário realizar uma busca mais direcionada, como o plugin de geração de relatório, utilize "maven plugins test report".

-  Queremos transformar todo o conteúdo da nossa aplicação em um arquivo .jar. No arquivo pom.xml, vimos que o <packaging> é do tipo JAR.

- Para que o Maven "empacote" nosso programa, pediremos que o objetivo package seja executado.
```mvn package```

- Para executarmos produtos-1.0-SNAPSHOT.JAR, escreveremos java -cp produtos-1.0-SNAPSHOT.jar br.com.alura.maven.App, isto é, incluiremos o arquivo em um classpath -cp, e executaremos a classe br.com.alura.maven.App.

<a name="anc2"></a>

## Maven no Eclipse
### Importando projeto maven existente
- File > Import
    - Maven > Existing Maven Projects
- Atalho Eclipse
    - Ctrl + 3
        - Quick Access do Eclipse". Digite "IEMP"
             - Import Existing Maven Projects

- [Xstream](https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream)

> Uma alternativa para o gerenciamento de dependências
- ivy


<a name="anc3"></a>

## Repositório remoto e local

- [Dependência do GSON](http://mvnrepository.com/artifact/com.google.code.gson/gson)

- Quando fazemos o comando com a flag `-o` ele não faz uma busca pelas dependência na internet, ela busca apenas local, deixando o processo bem mais rápido.
```
mvn -o test
mvn -o compile
```

- Imaginemos a criação de um projeto chamado blog. Nós já baixamos todos esses plugins do Maven, portanto ele não irá realizar o download novamente, e criará o projeto pulando essa etapa.

```
mvn archetype:generate -DartifactId=blog -DgroupId=br.com.alura.maven -Dinteractive
Mode=false -DarchetypeArtifactId=maven-archetype-quickstart 
```
- Repositorio local onde ficam todas as dependências baixadas `.m2/repository`



<a name="anc4"></a>

## Relatórios de qualidade e cobertura
### As fases que um ciclo de vida build apresenta:
    - 1. **validate** Validação: verificamos se projeto possui todas as informações necessárias
    - 2. **compile** Compilação: compilar os conteúdos
    - 3. **test** Teste: realizar testes diferentes no projeto
    - 4. **package** Pacote: geração de um pacote do projeto
    - 5. **integration-test** Teste de integração: realizar testes de integração
    - 6. **verify** Verificação: checagem do pacote gerado
    - 7. **install** Instalação: realizar a instalação do pacote no repositório local
    - 8. **deploy** Implantação: realizar a implantação no ambiente adequado

- Quando acionamos o seguinte comando no terminal estamos executando duas fases do ciclo: validação e compilação.

```
cd Documents/guilherme/workspace/produtos
mvn compile
```

- Podemos forçar a ordem de etapas com opções da linha de comando, como não gerar testes, por meio do comando -DskipTests=true:
```
mvn -DskipTests=true package
```

### O  PMD, que analisa o código fonte e detecta possíveis margens de bug no código. Para gerar este relatório utilizamos o comando pmd:pmd no terminal
- [PMD](https://maven.apache.org/plugins/maven-pmd-plugin/)

```
mvn pmd:pmd
```
- Ao final, será gerado um arquivo pmd.html, armazenado no diretório: `produtos > target > site`

- [Documentação do Maven relativa ao relatório PDM](https://maven.apache.org/plugins/maven-pmd-plugin/pmd-mojo.html)

-Na documentação do Maven relativa ao relatório PDM encontraremos as configurações possíveis e exemplos de uso, tais como análise de código JavaScript, Java Server Pages (JSP), como usar Rule Sets, ou regras determinadas. Esse recurso pode ser usado de diferentes maneiras para detectar possíveis pontos problemáticos no programa em desenvolvimento.

- Com o comando pmd:pmd conseguimos gerar relatórios, mas de que forma verificamos a qualidade do nosso projeto? Lembrando que a verificação é uma fase do ciclo de vida do build.

- Se simplesmente utilizarmos o comando `mvn verify` no terminal, não teremos uma verificação efetiva, afinal não configuramos o PMD para ser utilizado no momento da verificação. Para isso, utilizaremos o comando `pmd:check`, que realiza uma varredura no build à procura de erros, inclusive interrompendo o projeto caso as regras definidas para o código não sejam cumpridas.

- Todas as vezes em que quisermos executar o PMD precisamos utilizar `pmd:check` no terminal, o que pode se tornar cansativo ao longo do desenvolvimento do programa. No arquivo pom.xml, podemos realizar configurações que permitam a execução automática do PDM durante o build do projeto.

- Indicando que ele alterará o ciclo de vida do projeto. No arquivo pom.xml, adicionaremos a tag <executions> para especificarmos quando o plugin deverá ser executado, afinal podem haver múltiplas execuções ao longo do build. Em nosso caso, será apenas uma execução na fase (<phase>) de verificação (verify), cujo objetivo (<goals>) é check.

```
<project>
  <!-- ... -->
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-pmd-plugin</artifactId>
        <version>3.10.0</version>
        <executions>
            <execution>
                <phase>verify</phase>
                    <goals>
                        <goal>check</goal>
                    </goals>
            </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
  <!-- ... -->
</project>
```

### JACOCO
- O JaCoCo é o plugin padrão de cobertura de testes em Java.
- Adicione o plugin do JaCoCo ao projeto para que seja possível gerar relatórios sobre a cobertura de testes do projeto. Esse tipo de relatório é interessante pois é possível ver de uma forma fácil o que não testamos em nossa aplicação.
#### Usagem
```
...
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
        <goals>
            <goal>prepare-agent</goal>
            <goal>report</goal>
        </goals>
        </execution>
    </executions>
</plugin>
...
```
- Em nosso terminal executaremos o goal help — todos os goals do plugin estão registrados na documentação — por meio do comando `mvn jacoco:help`.
- Para o plugin gerar a cobertura do nosso código, existem duas fases a serem cumpridas: preparação do agente (**prepare-agent**) e a geração do relatório (**report**).
- Fase padrão dele é o `verify`
- No Eclipse, teremos o seguinte aviso de erro na parte inferior da tela: Maven Problems (1 item) > Project configuration is not up-to-date with pom.xml.. Ou seja, configuração do projeto não está atualizada com o arquivo pom.xml.
- Para resolvermos este problema acionaremos o atalho "Ctrl + 3" e acessaremos o buscador do Eclipse. Procuraremos pela opção "Update Project" e clicaremos sobre ela.

- Executaremos a verificação novamente, na linha de comando, por meio de mvn verify. Poderemos acessar o relatório de cobertura que foi armazenado em "produtos > target > site > jacoco > index.hrml".

![Relatório Jacoco](./asserts/relatorio_jacoco.png)
- Após inclusão de um teste
![Relatório Jacoco](./asserts/relatorio_jacoco_testes.png)

### Em projetos com muitas dependências, com o passar do tempo elas podem ficar desatualizadas, caso você queira atualizar as dependências para suas versões mais atuais, poderá utilizar o seguinte comando:
```
mvn versions:use-latest-versions
```

- [Doc](https://www.mojohaus.org/versions/versions-maven-plugin/use-latest-versions-mojo.html)

- Existe um outro goal que verifica por atualizações sem de fato alterar o pom.xml:

```
mvn versions:display-dependency-updates
```
- [Versions Maven Plugin](https://www.mojohaus.org/versions/versions-maven-plugin/index.html)


<a name="anc5"></a>

### Criando um projeto web
- Na área "Project Explorer" clicaremos com o botão direito e selecionaremos as opções "New > Project". Na caixa de diálogo que se abre selecionaremos a opção "Maven > Maven Project".

- Na caixa de diálogo encontraremos o item "Catalog", que fornece diversas opções de catálogos, e nos escolheremos "All Catalogs".
- Escolheremos o arquétipo org.apache.maven.archetypes maven-archetype-webapp
- Uma Web App é uma aplicação web baseada na API Servlet do Java, que podemos "deployar" em um servidor que tenha suporte para ela, como Tomcat, Jetty ou JBoss. Assim feito, pressionaremos o botão "Next".
- Na próxima etapa teremos campos a serem preenchidos: "Group Id", em que escreveremos a informação br.com.alura.maven, e "Artifact Id", que chamaremos de lojaweb, isto é, o nome do nosso site. Em seguida, na opção "Package" escolheremos a versão 1.0.0-SNAPSHOT

- Dos três servidores mencionados, o Jetty é o mais leve, e por este motivo o usaremos em nosso projeto. Sua documentação possui alguns goals em destaque, sendo um deles jetty:run.

- [Jetty Doc](https://eclipse.dev/jetty/documentation/jetty-9/index.html)

> Config maven

```
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>3.8.1</version>
        <scope>test</scope>
    </dependecy>
<dependencies>
<build>
    <finalName>lojaweb</finalName>
    <plugins>
        <plugin>
            <groupId>org.eclipse.jetty</groupId>
            <artifactId>jetty-maven-plugin</artifactId>
            <version>9.3.7.v20160115</version>
            <configuration>
                <scanIntervalSeconds>10</scanIntervalSeconds>
                <webApp>
                    <contextPath>/loja</contextPath>
                </webApp>
            </configuration>
        </plugin>
    </plugins>
</build>
```

- Na linha de comando, acessaremos o diretório lojaweb. Em seguida compilaremos o projeto utilizando o comando mvn compile.
- Por enquanto não temos nenhuma classe, portanto não há nada para ser compilado. Executaremos o comando mvn jetty:run para instalar o plugin em nosso projeto. Ao final do procedimento de instalação, seremos avisados de que o Jetty pode ser executado na porta 8080. No browser, acessaremos o endereço localhost:8080, e veremos a mensagem "Hello World!".

### Adicionando servlet

```
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
```

- Modificando versão do `web.xml` par ser igual a dependencia no pom.xml
```
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
    http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
    version="3.1">
</web-app>
```
- Executaremos o comando mvn jetty:run. O servidor está funcionando perfeitamente, e a mensagem é exibida no localhost.

- Na source folder src/main/java, dentro do pacote br.com.alura.maven.lojaweb, crie a Servlet ContatoServlet (Botão direito em src/main/java, "New > Class").

```
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/contato"})
public class ContatoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><h2>Entre em contato</h2></html>");
        writer.close();
    }
}
```

- [Acesso](http:localhost:8080/loja/contato)
<a name="anc6"></a>

## Projeto web