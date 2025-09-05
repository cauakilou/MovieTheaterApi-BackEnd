package BackEnd.MovieTheatherAPI.Model.Utils;

import BackEnd.MovieTheatherAPI.Model.Entity.RoomEntity;
import BackEnd.MovieTheatherAPI.Model.Entity.SeatEntity;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
public class SeatsCreate {


    public static ArrayList<SeatEntity> criarAssentos(int linha, int coluna, RoomEntity room){
        ArrayList<SeatEntity> conjuntoDeAssentos = new ArrayList<>();
        for (int i=0;i<linha;i++){
            for (int j=1;j<coluna+1;j++){
                var assento = new SeatEntity();
                assento.setFileira(AlfabetoStream.getLetraNaPosicao(i));
                assento.setNumero(j);
                assento.setRoom(room);
                conjuntoDeAssentos.add(assento);

            }
        }
        return conjuntoDeAssentos;
    }
}
