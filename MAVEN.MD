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



<a name="anc4"></a>

## Relatórios de qualidade e cobertura
cap 5


<a name="anc5"></a>

## Criando um projeto web

cap 6
<a name="anc6"></a>

## Projeto web