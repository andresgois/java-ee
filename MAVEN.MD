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

<a name="anc2"></a>

## Maven no Eclipse
cap 2


<a name="anc3"></a>

## Repositório remoto e local
cap 3


<a name="anc4"></a>

## Relatórios de qualidade e cobertura
cap 5


<a name="anc5"></a>

## Criando um projeto web

cap 6
<a name="anc6"></a>

## Projeto web