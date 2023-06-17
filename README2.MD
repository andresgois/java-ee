# JAVA EE

### Módulo 4
- [Do Java EE ao Jakarta EE Ver primeiro vídeo](#anc1)
- [Apresentando EJB’s e o projeto de agendamento de e-mails](#anc2)
- [Persistência com JPA](#anc3)
- [Fazendo requisição ao nosso EJB com JAX-RS](#anc4)
- [Agendamento com TimerService](#anc5)
- [Mensageria com JMS](#anc6)
- [Controle de transação com JTA](#anc7)

##

<a name="anc1"></a>

## Do Java EE ao Jakarta EE Ver primeiro vídeo
- Boas-vindas ao curso de EJB com Jakarta EE. Antes de partimos para o desenvolvimento da aplicação que será apresentada aqui no curso eu gostaria de passar com vocês nos slides e mostrar o que é o Jakarta EE.

- Tem tido muitas dúvidas sobre o assunto e sempre leva aquela famosa pergunta: o Java morreu? O Java vai morrer? 
    - A resposta é não. Na verdade, 
- o que houve foi uma *transição* entre empresas e foi necessária a troca do nome, mas para entender melhor essa troca nós vamos conhecer um pouco da história de como começou o Java EE.
    - História
    - Arquitetura
    - Sistema de API's
    - Containers

- Para isso eu trouxe alguns pontos que serão comentados: como surgiu o Java EE, a arquitetura, o que é esse sistema de API’s, o que são Containers, para entendermos todo esse cenário até chegar lá no Jakarta EE.

- Falando de história, como surgiu o Java EE? Na época a ASSAM era a atual detentora do Java e isso estou falando dos anos 90, e para suprir uma necessidade da Internet foi pensado um projeto Java corporativo para sistemas corporativos e o nome dele foi J2EE que era Java 2 Plataform Entrepise Edition.
    - Aplicações Desktop
    - Crescimento da internet
    - Projeto corporativo

- A ideia da ASSAM era escrever um conjunto de especificações onde pessoas poderiam implementar essas especificações e ter implementações para essas especificações criadas para o mundo corporativo.

### Arquitetura
- E para isso pensou-se em uma arquitetura, nós tínhamos o J2EE Platform, que era a plataforma que seguia um conjunto de API's, então essas especificações foram pensadas em API’s que teriam que ter as suas implementações. E poderia ser feito por terceiros ou por pessoas dentro da sua empresa que quisessem usar essas API's. 
        - J2EE Platform 
        - J2EE Compability Test Suit
        - J2EE Reference Implementation 

- Só que quando eu estou falando em seguir essas especificações, implementar essas API's, quando eu queria ter algo com implementação válida daquela minha especificação eu tinha o J2EE Compability Test Suit que testava se uma implementação era compatível com o padrão que eu tinha da minha API, da minha especificação.

- E também eu tinha mais um recurso nessa minha arquitetura que era a J2EE Reference Implementation que era uma implementação padrão. Porque, pensem bem, o Java EE tinha especificação mas aquela API precisava ser implementada, só que as empresas terceiras ainda iam ter acesso a especificação e iria levar um tempo para implementar aquela determinada API.

- Só que eu, desenvolvedor, muitas vezes já queria ter acesso aos recursos padronizados pela minha API, então eu já recebia essa implementação de referência. Depois se eu quisesse trocar por uma implementação de terceiros eu fazia isso de uma forma fácil porque a API é um conjunto de interfaces padronizadas de acordo com a necessidade dos sistemas corporativos.

- Então a ideia era justamente facilitar essa troca ou não ficar vinculado à uma empresa terceira, a uma implementação específica.

- Falando mais um pouco do sistema de API's nós podemos ver que o objetivo era desenvolver esse sistema e especificar um padrão de desenvolvimento. Então dessa forma nós poderíamos trocar de implementação sem muita dor no código.

### Sistema de API’s
- Esse sistema de API’s foi bem interessante para o Java EE e eu trouxe algumas que foram criadas, algumas API's, algumas especificações que fizeram parte das primeiras lições do Java EE.
    - Enterprise Java Beans (EJB)
    - JavaServer Pages (JSP)
    - Java Servlet
    - Java Database Connectivity

- Os EJB’s que são os Entreprise Java Jeans que são famosos até hoje e inclusive nós vamos falar sobre eles ao longo do curso. Nós tivemos o JSP (JavaServer Pages) e estão todas API's válidas, especificações válidas, algumas são bastante utilizadas outras nem tanto, mas ainda estão aí no Java EE até hoje. Nós temos o Java Servlet, Java Database Connectivity.

### Container
- Nós tínhamos a necessidade de deploiar essas aplicações corporativas em algum lugar, então temos Containers que são servidores de aplicações e eles são divididos em duas categorias: Web Container e EJB Container.
    - Web Container
    - EJB Container

- A diferença deles é que Web Container não vai ter a implementação de todos os serviços do Java EE, ele vai implementar alguns serviços mais voltados ali para o HTTP e a ideia dele é executar e controlar a execução das servlets.

- Já o EJB Container não, a aplicação EJB quando eu uso um Containers, um servidor de aplicação que implementa um EJB por exemplo, nós estamos falando de um Container muito mais robusto, então ele não vai me fornecer a implementação de todos os serviços do Java EE.

- Inclusive o GlassFish é o servidor de aplicação de referência, mas nada impede de você usar um WideFly, um Webson e todos eles vão ser esses servidores mais robustos e vão te entregar esses recursos todos implementados para facilitar a vida do desenvolvedor.

### Jakarta EE
- Agora conhecendo essas questões das API's, das especificações, das implementações você pode estar se perguntando porque houve essa troca para o Jakarta EE? A verdade é que a Oracle passou a focar e investir em outras tecnologias. O Java EE já estava ficando um pouco de lado. E é um plataforma extremamente utilizada mundialmente.

- Para que isso não viesse a atrasar a melhoria do Java e acompanhar as plataformas que estavam sendo atualizadas frequentemente, decidiu-se passar o direito do Java EE para a Eclipse Foundation. Só que o nome do Java é propriedade da Oracle, então o nome precisava ser alterado e por isso passou a ser Jakarta EE.

- A ideia é a mesma, é o projeto continuar com a ideia das especificações, das implementações só que agora com uma empresa que vai continuar levando o Jakarta como uma tecnologia de ponta trazendo API's que hoje cubram cenários, por exemplo, como a Cloud entre outras tecnologias mais atuais.

- Então para quem tinha essa dúvida sobre o Java estar morrendo, o Jakarta ser uma coisa totalmente diferente do Java não se preocupem. O Java continua mais vivo do que nunca e agora com ideia de que nós desenvolvedores temos os recursos mais novos só que com esse nome agora Jakarta que nada mais é só uma questão de direito autoral.

> servidor de aplicação J2EE
- Os servidores de aplicação implementam os serviços do Java/Jakarta EE.

<a name="anc2"></a>

## Apresentando EJB’s e o projeto de agendamento de e-mails

<a name="anc3"></a>

## Persistência com JPA

<a name="anc4"></a>

## Fazendo requisição ao nosso EJB com JAX-RS

<a name="anc5"></a>

## Agendamento com TimerService

<a name="anc6"></a>

## Mensageria com JMS

<a name="anc7"></a>

## Controle de transação com JTA

