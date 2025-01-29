function grupoObrigatorio() {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-danger");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = "Selecione um grupo para o jogador!";
}

function jogadorCadastrado() {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-success");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = "Jogador cadastrado com sucesso! Clique em Listar Jogadores para obter a lista atualizada";
}

function erroCadastro(mensagem) {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-danger");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = mensagem;
}

function semConexao() {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-danger");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = "Erro na conexão com o servidor!";
}

function limpaRetornoCadastro() {
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.add("hidden");
    divRetornoCadastro.classList.remove("alert");
    divRetornoCadastro.classList.remove("alert-danger");
    divRetornoCadastro.classList.remove("alert-success");
    divRetornoCadastro.classList.remove("alert-info");
    divRetornoCadastro.innerText = "";
}

function nenhumJogadorCadastrado() {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-info");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = "Nenhum jogador cadastrado";
}

function erroListagem(mensagem) {
    limpaRetornoCadastro();
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    divRetornoCadastro.classList.remove("hidden");
    divRetornoCadastro.classList.add("alert");
    divRetornoCadastro.classList.add("alert-danger");
    divRetornoCadastro.setAttribute("role", "alert");
    divRetornoCadastro.innerText = mensagem;
}

function exibirMensagemCarregando() {
    const divCarregando = document.getElementById("msg_carregando");
    divCarregando.classList.remove("hidden");
}

function ocultarMensagemCarregando() {
    const divCarregando = document.getElementById("msg_carregando");
    setTimeout(() => divCarregando.classList.add("hidden"), 300);
}

document.addEventListener("DOMContentLoaded", () => {
    const btnCadastrar = document.querySelector("input[value='Cadastrar']");
    const btnListar = document.querySelector("input[value='Listar Jogadores']");
    const divRetornoCadastro = document.getElementById("retorno_cadastro");
    const divJogadoresCadastrados = document.getElementById("jogadores_cadastrados");

    btnCadastrar.addEventListener("click", async () => {
        exibirMensagemCarregando();
        divRetornoCadastro.innerHTML = "<div id='retorno_cadastro'></div>"
        const nome = document.getElementById("nome").value;
        const email = document.getElementById("email").value;
        const telefone = document.getElementById("telefone").value || null;
        const grupo = parseInt(document.querySelector("input[name='grupo']:checked")?.value);

        if (!grupo) {
            ocultarMensagemCarregando();
            grupoObrigatorio();
            return;
        }

        const jogador = { nome, email, telefone, grupo };

        try {
            const response = await fetch("http://localhost:8080/api/jogador", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(jogador),
            });
            ocultarMensagemCarregando();

            if (response.status === 201) {
                jogadorCadastrado();
            } else if (response.status === 400) {
                const errorData = await response.json();
                console.log(errorData);
                erroCadastro(errorData.mensagem);
            } else {
                erroCadastro("Ocorreu um erro ao cadastrar o jogador. Status: " + response.status);
            }
        } catch (error) {
            ocultarMensagemCarregando();
            semConexao();
        }
    });

    btnListar.addEventListener("click", async () => {
        exibirMensagemCarregando();
        limpaRetornoCadastro();

        try {
            const response = await fetch("http://localhost:8080/api/jogador");
            ocultarMensagemCarregando();

            if (response.ok) {
                const jogadores = await response.json();

                if (jogadores.length > 0) {
                    const tabela = `
                        <div id='retorno_cadastro'>
                            <table border='1' class='table table-striped text-center'>
                                <thead>
                                    <tr>
                                        <th scope='col'>Nome</th>
                                        <th scope='col'>E-mail</th>
                                        <th scope='col'>Telefone</th>
                                        <th scope='col'>Codinome</th>
                                        <th scope='col'>Grupo</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${jogadores
                                        .map(
                                            (jogador) => `
                                        <tr>
                                            <td>${jogador.nome}</td>
                                            <td>${jogador.email}</td>
                                            <td>${jogador.telefone}</td>
                                            <td>${jogador.codinome}</td>
                                            <td>${jogador.grupo}</td>
                                        </tr>
                                    `
                                        )
                                        .join("")}
                                </tbody>
                            </table>
                        </div>
                    `;
                    divJogadoresCadastrados.innerHTML = tabela;
                } else {
                    nenhumJogadorCadastrado();
                }
            } else {
                erroListagem("Ocorreu um erro ao listar os jogadores. Status: " + response.status);
            }
        } catch (error) {
            ocultarMensagemCarregando();
            semConexao();
        }
    });
});
