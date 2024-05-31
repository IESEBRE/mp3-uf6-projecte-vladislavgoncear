package org.example.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DAOException extends Exception{

        private static final Map<Integer, String> missatges = new HashMap<>();
        //num i retorna string, el map
        static {
            missatges.put(0, "Error al connectar a la BD!!");
            missatges.put(1, "Restricció d'integritat violada - clau primària duplicada");
            missatges.put(904, "Nom de columna no vàlid");
            missatges.put(936, "Falta expressió en l'ordre SQL");
            missatges.put(942, "La taula o la vista no existeix");
            missatges.put(1000, "S'ha superat el nombre màxim de cursors oberts");
            missatges.put(1400, "Inserció de valor nul en una columna que no permet nuls");
            missatges.put(1403, "No s'ha trobat cap dada");
            missatges.put(1722, "Ha fallat la conversió d'una cadena de caràcters a un número");
            missatges.put(1747, "El nombre de columnes de la vista no coincideix amb el nombre de columnes de les taules subjacents");
            missatges.put(4091, "Modificació d'un procediment o funció en execució actualment");
            missatges.put(6502, "Error numèric o de valor durant l'execució del programa");
            missatges.put(12154, "No s'ha pogut resoldre el nom del servei de la base de dades Oracle o l'identificador de connexió");
            missatges.put(2292, "S'ha violat la restricció d'integritat -  s'ha trobat un registre fill");
            missatges.put(2,"El num de producte ha de ser en majuscula, 7 caracters i un numero al final (Exemple: ABCDEFG1)");
            missatges.put(3,"Has de introduir una talla correcta (Exemple: 42.5)");
            missatges.put(4,"Has de seleccionar una fila per a poder borrar-la");
            missatges.put(5,"Has de seleccionar una fila per a poder modificar-la");
            missatges.put(6,"Ja existeix un numero de producte amb aquesta referencia");
            missatges.put(7,"Falta omplir alguna dada");
            missatges.put(8,"S'ha produit un error al llegir les dades");
            missatges.put(9,"S'ha produit un error al guardar les dades");
            missatges.put(10,"Has de modificar alguna dada");
            missatges.put(11,"Has de introduir un format de data correcte (Exemple: 10/10/2021)");
            missatges.put(12,"No hi ha cap informacio per filtrar");
            missatges.put(13,"No hi ha productes disponibles");
            missatges.put(14,"Error al llegir el fitxer de numeros de producte");
            missatges.put(15,"Error al guardar les dades a la BD");
            missatges.put(16,"Error al borrar la fila a la BD");
            missatges.put(17,"No es pot canviar el numero de producte a un producte existent");
            missatges.put(18,"No hi ha cap Sneaker registrar a la BD amb aquest numero de producte");
            missatges.put(19,"Error al llegir el arxiu de connexio a la BD");
            missatges.put(20,"Error al llegir el arxiu sql de creacio de la taula");
        }

        //atribut
        private int tipo;

        //constructor al q pasem tipo
        public DAOException(int tipo){
            this.tipo=tipo;
        }

        //sobreescrivim el get message
        @Override
        public String getMessage(){
            return missatges.get(this.tipo); //el missatge del tipo
        }

        public int getTipo() {
            return tipo;
        }
    }
