package com.lexer.iftm;

public class Sintatico {
    private Lexico lexico;
    private Token token;

    private int linha;

    private int coluna;

    public void Ler(){
        token = lexico.getToken(linha, coluna);
        System.out.println(token);
        coluna = token.getColuna()+token.getTamanhoToken();
        linha = token.getLinha();
    }

    public Sintatico(String nomeArquivo) {
        linha=1;
        coluna=1;
        this.lexico = new Lexico(nomeArquivo);
    }

    public void analisar() {
        Ler();
        programa();
    }

    public void mensagemErro(String msg) {
        System.err.println("Linha: " + token.getLinha() +
                ", Coluna: " + token.getColuna() +
                msg);
    }

    public void programa() {
        if ((token.getClasse() == Classe.cPalRes)
                && (token.getValor().getValorIdentificador().equalsIgnoreCase("program"))) {
            Ler();
            if (token.getClasse() == Classe.cId) {
                Ler();
                corpo();
                if (token.getClasse() == Classe.cPonto) {
                    Ler();
                } else {
                    mensagemErro(" - DEVE ENCERRAR COM PONTO(\".\")");
                }
            } else {
                mensagemErro(" - DEVE IDENTIFICAR O NOME DO PROGRAMA");
            }
        } else {
            mensagemErro(" - DEVE INICIAR COM \"PROGRAMA\"");
        }
    }

    public void corpo() {
        declara();
        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("begin"))) {
            Ler();
            sentencas();
            if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("end"))) {
                Ler();
            }else {
                mensagemErro(" - DEVE TERMINAR COM \"END\"");
            }
        }else {
            mensagemErro(" - DEVE CONTER \"BEGIN\" NO CORPO");
        }
    }

    public void declara() {
        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("var"))) {
            Ler();
            dvar();
            mais_dc();
        }
    }

    public void mais_dc() {
        if (token.getClasse() == Classe.cPontoVirgula) {
            Ler();
            cont_dc();
        } else {
            mensagemErro(" - DEVE COLOCAR PONTO E VIRGULA(\";\")");
        }
    }

    public void cont_dc() {
        if (token.getClasse() == Classe.cId) {
            dvar();
            mais_dc();
        }
    }

    public void dvar() {
        variaveis();
        if (token.getClasse() == Classe.cDoisPontos) {
            Ler();
            tipo_var();
        }else {
            mensagemErro(" - DEVE TER DOIS PONTOS(\":\")");
        }
    }

    public void tipo_var() {
        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("integer"))) {
            Ler();
        }else if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("real"))) {
            Ler();
        }else {
            mensagemErro(" - DEVE-SE DECLARAR O INTEGER");
        }
    }

    public void variaveis() {
        if (token.getClasse() == Classe.cId) {
            Ler();
            mais_var();
        }else {
            mensagemErro(" - DEVE SE COLOCAR O IDENTIFICADOR");
        }
    }

    public void mais_var(){
        if (token.getClasse() == Classe.cVirgula) {
            Ler();
            variaveis();
        }
    }


    public void sentencas() {
        comando();
        mais_sentencas();
    }


    public void mais_sentencas() {
        if (token.getClasse() == Classe.cPontoVirgula) {
            Ler();
            cont_sentencas();
        }else {
            mensagemErro(" - DEVE SE TER PONTO E VIRGULA(\";\")");
        }
    }



    public void cont_sentencas() {
        if (((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("read"))) ||
                ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("write"))) ||
                ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("for"))) ||
                ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("repeat"))) ||
                ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("while"))) ||
                ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("if"))) ||
                ((token.getClasse() == Classe.cId))
        ) {
            sentencas();
        }
    }


    public void var_read() {
        if (token.getClasse() == Classe.cId) {
            Ler();
            mais_var_read();
        }else {
            mensagemErro(" - DEVE TER O IDENTIFICADOR");
        }
    }


    public void mais_var_read() {
        if (token.getClasse() == Classe.cVirgula) {
            Ler();
            var_read();
        }
    }



    public void var_write() {
        if (token.getClasse() == Classe.cId) {
            Ler();
            mais_var_write();
        }else {
            mensagemErro(" - DEVE TER O IDENTIFICADOR");
        }
    }


    public void mais_var_write() {
        if (token.getClasse() == Classe.cVirgula) {
            Ler();
            var_write();
        }
    }

    public void comando() {

        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("read"))){
            Ler();
            if (token.getClasse() == Classe.cParEsq) {
                Ler();
                var_read();
                if (token.getClasse() == Classe.cParDir) {
                    Ler();
                }else {
                    mensagemErro(" - DEVE SE FECHAR O PARENTESES DIREITO )");
                }
            }else {
                mensagemErro(" - DEVE SE FECHAR O PARENTESES ESQUERDO (");
            }
        }else
            //write ( <var_write> ) |
            if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("write"))){
                Ler();
                if (token.getClasse() == Classe.cParEsq) {
                    Ler();
                    var_write();
                    if (token.getClasse() == Classe.cParDir) {
                        Ler();
                    }else {
                        mensagemErro(" - DEVE SE FECHAR O PARENTESES DIREITO )");
                    }
                }else {
                    mensagemErro(" - DEVE SE FECHAR O PARENTESES ESQUERDO (");
                }
            }else

                if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("for"))){
                    Ler();
                    if (token.getClasse() == Classe.cId) {
                        Ler();

                        if (token.getClasse() == Classe.cAtribuicao){
                            Ler();
                            expressao();
                            if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("to"))){
                                Ler();
                                expressao();
                                if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("do"))){
                                    Ler();
                                    if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("begin"))){
                                        Ler();
                                        sentencas();
                                        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("end"))){
                                            Ler();
                                        }else {
                                            mensagemErro(" - DEVE SE FINALIZAR O FOR COM UM \"END\"");
                                        }
                                    }else {
                                        mensagemErro(" - DEVE CONTER UM \"BEGIN\" NO FOR");
                                    }
                                }else {
                                    mensagemErro(" - DEVE CONTER O \"DO\" NO FOR");
                                }
                            }else {
                                mensagemErro(" - DEVE CONTER O \"TO\" NO FOR");
                            }
                        }else {
                            mensagemErro(" - DEVE CONTER DOIS PONTOS(\":\") E IGUAL(\"=\") NO FOR");
                        }
                    }else {
                        mensagemErro(" - DEVE TER O IDENTIFICADOR DO FOR NO COMECO DA INSTRUCAO");
                    }
                }else

                    if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("repeat"))){
                        Ler();
                        sentencas();
                        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("until"))){
                            Ler();
                            if (token.getClasse() == Classe.cParEsq){
                                Ler();
                                condicao();
                                if (token.getClasse() == Classe.cParDir){
                                    Ler();
                                }else {
                                    mensagemErro(" - DEVE SE FECHAR OS PARENTESES NA INSTRUCAO DE REPEAT");
                                }
                            }else {
                                mensagemErro(" - DEVE SE ABRIR OS PARENTESES NA INSTRUCAO DE REPEAT");
                            }
                        }else {
                            mensagemErro(" - DEVE CONTER O \"UNTIL\" NA INSTRUCAO DE REPEAT");
                        }
                    }

                    else if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("while"))){
                        Ler();
                        if (token.getClasse() == Classe.cParEsq){
                            Ler();
                            condicao();
                            if (token.getClasse() == Classe.cParDir){
                                Ler();
                                if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("do"))){
                                    Ler();
                                    if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("begin"))){
                                        Ler();
                                        sentencas();
                                        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("end"))){
                                            Ler();
                                        }else {
                                            mensagemErro(" - DEVE TERMINAR O WHILE COM \"END\"");
                                        }
                                    }else {
                                        mensagemErro(" - DEVE COMECAR O WHILE COM \"BEGIN\"");
                                    }
                                }else {
                                    mensagemErro(" - DEVE CONTER O \"DO\" NO WHILE");
                                }
                            }else {
                                mensagemErro(" - DEVE SE FECHAR O PARENTESES DIREITO(\")\") NO WHILE");
                            }
                        }else {
                            mensagemErro(" - DEVE SE ABRIR O PARENTESES ESQUERDO(\"(\") NO WHILE");
                        }
                    }
                    else if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("if"))){
                        Ler();
                        if (token.getClasse() == Classe.cParEsq){
                            Ler();
                            condicao();
                            if (token.getClasse() == Classe.cParDir){
                                Ler();
                                if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("then"))){
                                    Ler();
                                    if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("begin"))){
                                        Ler();
                                        sentencas();
                                        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("end"))){
                                            Ler();
                                            pfalsa();
                                        }else {
                                            mensagemErro(" - DEVE TERMINAR O WHILE COM \"END\" NO WHILE");
                                        }
                                    }else {
                                        mensagemErro(" - DEVE COMECAR O WHILE COM \"BEGIN\"");
                                    }
                                }else {
                                    mensagemErro(" - DEVE CONTER O \"DO\" NO WHILE");
                                }
                            }else {
                                mensagemErro(" - DEVE SE FECHAR O PARENTESES DIREITO(\")\") NO WHILE");
                            }
                        }else {
                            mensagemErro(" - DEVE SE ABRIR O PARENTESES ESQUERDO(\"(\") NO WHILE");
                        }
                    }
                    else if (token.getClasse() == Classe.cId){
                        Ler();
                        if (token.getClasse() == Classe.cAtribuicao){
                            Ler();
                            expressao();
                        }
                        else {
                            mensagemErro(" - DEVE SE TER A ATRIBUICAO");
                        }
                    }
    }

    public void condicao() {
        expressao();
        relacao();
        expressao();
    }


    public void pfalsa() {
        if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("else"))){
            Ler();
            if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("begin"))){
                Ler();
                sentencas();
                if ((token.getClasse() == Classe.cPalRes) && (token.getValor().getValorIdentificador().equalsIgnoreCase("end"))){
                    Ler();
                }else {
                    mensagemErro(" - DEVE SE FINALIZAR COM \"END\"");
                }
            }else {
                mensagemErro(" - DEVE SE INICIALIZAR COM \"BEGIN\"");
            }
        }
    }

    public void relacao() {
        if (token.getClasse() == Classe.cIgual) {
            Ler();
        }else if (token.getClasse() == Classe.cMaior) {
            Ler();
        }else if (token.getClasse() == Classe.cMenor) {
            Ler();
        }else if (token.getClasse() == Classe.cMaiorIgual) {
            Ler();
        }else if (token.getClasse() == Classe.cMenorIgual) {
            Ler();
        }else if (token.getClasse() == Classe.cDiferente) {
            Ler();
        }else {
            mensagemErro(" - DEVE SE CONTER O OPERADOR DE RELACAO");
        }
    }

    public void expressao() {
        termo();
        outros_termos();
    }

    public void outros_termos() {
        if (token.getClasse() == Classe.cMais || token.getClasse() == Classe.cMenos) {
            op_ad();
            termo();
            outros_termos();
        }
    }

    public void op_ad() {
        if (token.getClasse() == Classe.cMais || token.getClasse() == Classe.cMenos) {
            Ler();
        }else {
            mensagemErro(" - DEVE SE COLOCAR  OPERADOR DE ADICAO(\"+\") OU DE SUBTRACAO(\"-\")");
        }
    }

    public void termo() {
        fator();
        mais_fatores();
    }


    public void mais_fatores() {
        if (token.getClasse() == Classe.cMultiplicacao || token.getClasse() == Classe.cDivisao) {
            op_mul();
            fator();
            mais_fatores();
        }
    }

    public void op_mul() {
        if (token.getClasse() == Classe.cMultiplicacao || token.getClasse() == Classe.cDivisao) {
            Ler();
        }else {
            mensagemErro(" - DEVE POSSUIR A MULTIPLICACAO(\"*\") OU DIVISAO(\"\\\")");
        }
    }


    public void fator() {
        if (token.getClasse() == Classe.cId) {
            Ler();
        }else if (token.getClasse() == Classe.cInt || token.getClasse() == Classe.cReal) {
            Ler();
        }else if (token.getClasse() == Classe.cParEsq){
            Ler();
            expressao();
            if (token.getClasse() == Classe.cParDir){
                Ler();
            }else {
                mensagemErro(" - DEVE SE COLOCAR O PARENTESES DIREITO");
            }
        }else {
            mensagemErro(" - DEVE SE TER O FATOR \"IN\" NO EXP");
        }
    }
    
}
