# JAVA EE

### Módulo 6 JSF 2
- [Apresentação e teste de avaliação Ver primeiro vídeo](#anc1)
- [Começando com Primefaces](#anc2)
- [Componentes ricos no formulário](#anc3)
- [Mais usabilidade na página de livros](#anc4)
- [Dados tabulares com Primefaces](#anc5)
- [Melhorando a navegação com menus](#anc6)
- [Aplicando temas](#anc7)
- [Gráfico com Primefaces](#anc8)

# Módulo 6
<a name="anc1"></a>

## Apresentação e teste de avaliação Ver primeiro vídeo

### Apresentação do projeto a criar
- Nesse curso vamos focar os componentes da biblioteca famosa Primefaces! Com ela, não só iremos melhorar a interface da nossa aplicação, como também melhoraremos a sua usabilidade.

- Vou apresentar o projeto que criaremos nesse cursos e mostrar as telas já com Primefaces. Na página de login vemos um medidor de segurança de senhas, um menu para navegação e realizar o logout, máscaras nos campos, um calendário para selecionar a data, uma tabela paginada, onde podemos filtrar e ordenar os dados facilmente, além de uma nova página de vendas e diversas outras melhorias!

- Para vocês terem uma ideia de como a nossa aplicação ficará após o uso do Primefaces, seguem algumas imagens:

- [tela_com_prime](./asserts/tela_com_prime.png)

- Na especificação JSF, como definir uma classe gerenciada pelo JSF?
    - *@ManagedBean public class LivroBean {*
- Quais são as características comuns no desenvolvimento RAD (Rapid Application Development) e que também podem ser encontradas no desenvolvimento com JavaServer Faces?
    - *Componentes ricos, orientado ao evento e mantém o estado dos componentes (stateful).*
- Os componentes definidos na página xhtml serão instanciados pelo controlador padrão do JSF. O resultado disso é a árvore de componentes que fica guardada em memória. Em que momento o controlador cria esta árvore?
    - *Apenas na primeira requisição (no primeiro HTTP GET).*
- Qual é o efeito de anotar um bean gerenciado pelo JSF com a anotação @ViewScoped?
    - *A instância desse bean existirá enquanto a tela existir.*
- Cada componente possui atributos especiais para criar uma ligação com uma classe/bean. No caso do h:commandButton, que atributo devemos declarar para indicar que bean usaremos?
    - *action*
- JSF usa fases bem definidas que são executadas em cada requisição. O que acontece na terceira fase que se chama de PROCESS_VALIDATION?
    - *Na terceira fase acontece a conversão, se for preciso, e a validação.*

- Veja o pedaço de código xhtml:
```
<h:commandButton value="Gravar" action="#{livroBean.gravar}"  />
```
- O método gravar do livroBean associado com o comando em cima é executado em qual fase?
    - *INVOKE_APPLICATION*


<a name="anc2"></a>

## Começando com Primefaces

### Declaração do namespace
- [PrimeFaces](https://www.primefaces.org/)

- Os primeiros componentes do Primefaces
    - A ideia agora é utilizar cada vez mais os componentes do Primefaces. Vamos "primefacear" todo o conteúdo do formulário, começando pelo título "Login", ele passará a ser um <p:outputPanel>. Depois, o fieldset e legend, agora teremos um <p:fieldset>, que já tem um atributo legend, então podemos substituí-los. Após isso, a ideia é substituir todos os componentes padrões do JSF, que utilizam o prefixo h, pelo prefixo p, ou seja, o Primefaces não só define componentes novos, como também redefine os componentes padrões do JSF. A razão dele fazer isso é que o Primefaces dá "algo a mais" para esses componentes, pode ser que o componente já faça ajax, ou que já venha com um CSS aplicado, etc. Ou seja, não precisamos reaprender a API.

- `xmlns:p="http://primefaces.org/ui"`

### Ajax por padrão
- O botão do Primefaces já faz ajax por padrão, que não é um comportamento do commandButton padrão. Então, já que ele faz ajax por padrão, precisamos definir o que queremos submeter e quais partes da tela queremos renderizar, o que não estamos fazendo no momento, já que atualmente, quando clicamos no botão para efetuar login, só estamos submetendo o botão e não os campos do formulário. Então vamos mudar esse comportamento, para isso o Primefaces tem dois atributos, o process e o update. No process dizemos quais componentes queremos submeter, no nosso caso queremos submeter todo o formulário, então utilizaremos @form; e no update dizemos o que queremos atualizar na página, e no nosso caso também é o formulário, logo também terá como valor @form. Então o commandButton ficará assim:

```
<p:commandButton value="Efetue Login" action="#{loginBean.efetuaLogin}" update="@form" process="@form" />
```

- Tem um script para popular as tabelas na raiz do projeto.

- Sobre o Primefaces podemos dizer que:
    - A) São componentes que estendem os componentes padrões do JSF adicionando novas funcionalidades, inclusive criando componentes inéditos.
    - B) Permite aplicar um atrativo estético em nossas páginas sem conhecimento técnico de CSS com pouco esforço através do seu sistema de temas.
    - C) Abstrai muitas vezes o uso de bibliotecas como jQuery ou até mesmo o uso de JavaScript para tarefas corriqueiras como o uso máscaras e de calendários.

<a name="anc3"></a>

## Componentes ricos no formulário


<a name="anc4"></a>

## Mais usabilidade na página de livros

<a name="anc5"></a>

## Dados tabulares com Primefaces

<a name="anc6"></a>

## Melhorando a navegação com menus

<a name="anc7"></a>

## Aplicando temas

<a name="anc8"></a>

## Gráfico com Primefaces
