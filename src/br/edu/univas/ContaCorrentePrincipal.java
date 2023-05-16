package br.edu.univas;


public class ContaCorrentePrincipal {
    public static void main(String[] args) {
        ContaCorrente contaPrincipal = new ContaCorrente("Rafael", "111.222.333-44", "123456");
        contaPrincipal.depositar(2000);
        contaPrincipal.depositar(1800);
        contaPrincipal.getExtrato();
        ContaCorrente contaTeste = new ContaCorrente("Teste", "123.456.789-10", "654321");
        contaTeste.depositar(2000);
        contaTeste.getExtrato();
        System.out.println(contaPrincipal.toString());
    }
}