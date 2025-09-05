package BackEnd.MovieTheatherAPI.Model.Utils;

public class AlfabetoStream {
    public static char getLetraNaPosicao(int posicao) {
        // 1. Validamos se a posição está dentro do alfabeto (0 a 25)
        if (posicao >= 0 && posicao < 26) {
            // 2. A lógica principal: somamos a posição ao caractere inicial 'A'
            return (char) ('A' + posicao);
        } else {
            // 3. Lançamos um erro se a posição for inválida
            throw new IllegalArgumentException("A posição deve estar entre 0 e 25.");
        }
    }
}