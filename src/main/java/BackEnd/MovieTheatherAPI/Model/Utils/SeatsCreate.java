package BackEnd.MovieTheatherAPI.Model.Utils;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SeatsCreate {


    // Métudo agora retorna uma List<SeatEntity> e não precisa mais do objeto Room
    public static List<SeatEntity> criarAssentos(int linha, int coluna){
        List<SeatEntity> conjuntoDeAssentos = new ArrayList<>();
        for (int i=0; i < linha; i++){
            for (int j=1; j <= coluna; j++){
                SeatEntity assento = new SeatEntity();
                assento.setFileira(AlfabetoStream.getLetraNaPosicao(i));
                assento.setNumero(j);
                // Não definimos mais a sala aqui
                conjuntoDeAssentos.add(assento);
            }
        }
        return conjuntoDeAssentos;
    }
}

