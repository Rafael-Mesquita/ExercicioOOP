package br.edu.univas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ContaCorrente {
    private String titular;
    private double saldo;
    private String cpf;
    private String cartao;
    private String senha;
    private double limiteChequeEspecial;
    private double limiteCartao;
    private int numeroConta;
    private String agencia;
    private String[] chavePix;
    private double jurosCheque;
    private double valorEmJuizo;
    private ArrayList<String> extrato = new ArrayList<>();

    public ContaCorrente(String titular, String cpf, String senha) {
        this.setTitular(titular);
        this.setCpf(cpf);
        this.setSenha(senha);
        this.setSaldo(0);
        this.setLimiteChequeEspecial(1000);
        this.setLimiteCartao(0);
        this.setAgencia("0001");
        this.setNumeroConta((1000000 % new Random().nextInt()) + 1000);
        this.valorEmJuizo = 0.1;
    }

    @Override
    public String toString() {
        return "ContaCorrente | titular= " + titular + ", saldo= " + saldo + ", cpf= " + cpf + ", cartao= " + cartao
                + ", senha= " + senha + ", limiteChequeEspecial= " + limiteChequeEspecial + ", limiteCartao= "
                + limiteCartao + ", numeroConta= " + numeroConta + ", agencia= " + agencia + ", chavePix= "
                + Arrays.toString(chavePix) + ", jurosCheque= " + jurosCheque + ", valorEmJuizo= " + valorEmJuizo
                + ", extrato= " + extrato;
    }

    public void depositar(double valor) {
        if(this.getValorEmJuizo() > 0) {
            if(this.getSaldo() < 0) {
                valor += (this.getSaldo() * this.getJurosCheque());
                this.setLimiteChequeEspecial(this.getLimiteChequeEspecialTotal() + (valor - (this.getSaldo()*-1)));
                this.setExtrato(extrato, "Depósito no valor de R$ " + valor);
            }
            this.setSaldo(this.getSaldo() + (valor - (valor * this.getValorEmJuizo())));
            this.setExtrato(extrato, "Depósito no valor de R$ " + (valor - (valor * this.getValorEmJuizo())));
        } else {
            this.setSaldo(this.getSaldo() + valor);
            this.setExtrato(extrato, "Depósito no valor de R$ " + valor);
        }
    }

    public void sacar(double valor) {
        if(this.getLimiteChequeEspecialTotal() + this.getSaldo() >= valor) {
            this.setLimiteChequeEspecial(this.getLimiteChequeEspecialAtual() - (valor - this.getSaldo()));
            this.setSaldo(this.getSaldo() - valor);
            this.setLimiteChequeEspecial(this.getLimiteChequeEspecialAtual() - valor);
            this.setExtrato(extrato, "Saque no valor de R$ " + valor);
        } else {
            System.out.println("Não é possível realizar o saque!");
        }
    }

    public void transferir(ContaCorrente contaPrincipal, ContaCorrente contaATransferir, String agencia, int conta, double valor) {
        if(contaPrincipal.getSaldo() >= valor) {
            if(contaATransferir.getAgencia().equals(agencia) && contaATransferir.getNumeroConta() == conta) {
                contaPrincipal.setSaldo(contaPrincipal.getSaldo() - valor);
                contaPrincipal.setExtrato(extrato, "Transferência Enviada no valor de R$ " + valor);
                contaATransferir.setSaldo(contaATransferir.getSaldo() + valor);
                contaATransferir.setExtrato(extrato, "Transferência Recebida no valor de R$ " + valor);
            }
        }
    }

    public void transferirPix(ContaCorrente contaPrincipal, ContaCorrente contaATransferir, String pix, double valor) {
        try {
            if(contaPrincipal.getSaldo() >= valor) {
                boolean encontrado = false;
                for(int i = 0; i < contaATransferir.getChavePix().length; i++) {
                    if(contaATransferir.getChavePix()[i].equals(pix)) {
                        encontrado = true;
                    }
                } if(encontrado) {
                    contaPrincipal.setSaldo(contaPrincipal.getSaldo() - valor);
                    contaPrincipal.setExtrato(extrato, "Transferência PIX Enviada no valor de R$ " + valor);
                    contaATransferir.setSaldo(contaATransferir.getSaldo() + valor);
                    contaATransferir.setExtrato(extrato, "Transferência PIX Recebida no valor de R$ " + valor);
                } else {
                    System.out.println("Chave PIX não encontrada");
                }
            } else {
                System.out.println("Saldo Insuficiente");
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public String getTitular() {
        return this.titular + "[" + this.getCpf() + "]";
    }

    private void setTitular(String titular) {
        this.titular = titular;
    }

    public String getCpf() {
        return cpf;
    }

    private void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String[] getChavePix() {
        return chavePix;
    }

    private void setChavePix(String[] chavePix) {
        this.chavePix = chavePix;
    }

    public double getSaldo() {
        return saldo;
    }

    private void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    private void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    private void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public double getLimiteCartao() {
        return limiteCartao;
    }

    private void setLimiteCartao(double limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public String getSenha() {
        return senha;
    }

    private void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCartao() {
        return cartao;
    }

    private void setCartao(String cartao) {
        this.cartao = cartao;
    }
    public double getLimiteChequeEspecialAtual() {
        return limiteChequeEspecial;
    }
    public double getLimiteChequeEspecialTotal() {
        if(this.getSaldo() < 0) {
            return this.getLimiteChequeEspecialAtual() + (-1 * this.getSaldo());
        }else {
            return this.getLimiteChequeEspecialAtual();
        }
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    public void getExtrato() {
        for(int i = 0; i < extrato.size(); i++) {
            System.out.println(extrato.get(i));
        }
    }

    private void setExtrato(ArrayList<String> extrato, String historico) {
        this.extrato.add(historico);
    }

    public double getValorEmJuizo() {
        return valorEmJuizo;
    }

    private void setValorEmJuizo(double valorEmJuizo) {
        this.valorEmJuizo = valorEmJuizo;
    }
    public double getJurosCheque() {
        return jurosCheque;
    }

    private void setJurosCheque(double jurosCheque) {
        this.jurosCheque = jurosCheque;
    }
}