package br.com.streaming.util;

// concentrei todas as validacoes aqui pra nao ficar repetindo if null/blank em todo lugar
// final porque nao precisa ser herdada, é só um utilitario mesmo
public final class Validador {

    // construtor privado pra ninguem instanciar sem querer
    private Validador() {}

    public static void exigirTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " nao pode ser nulo ou vazio.");
        }
    }

    public static void exigirIntervalo(int valor, int min, int max, String campo) {
        if (valor < min || valor >= max) {
            throw new IllegalArgumentException(campo + " deve estar entre " + min + " e " + (max - 1) + ".");
        }
    }

    public static void exigirNaoNulo(Object obj, String campo) {
        if (obj == null) {
            throw new IllegalArgumentException(campo + " nao pode ser nulo.");
        }
    }

    public static boolean emailValido(String email) {
        return email != null && email.contains("@") && !email.isBlank();
    }
}