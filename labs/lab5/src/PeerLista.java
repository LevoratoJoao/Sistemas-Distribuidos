/**
 * Lab05: Sistema P2P
 *
 * Autor: João Vitor Levorato de Souza, Eduardo Yuji Yoshida Yamada
 * Ultima atualizacao: 10/12/2024
 *
 * Referencias:
 * https://docs.oracle.com/javase/tutorial/essential/io
 * http://fortunes.cat-v.org/
 */

public enum PeerLista {

    PEER1 {
        @Override
        public String getNome() {
            return "PEER1";
        }
    },
    PEER2 {
        public String getNome() {
            return "PEER2";
        }
    },
    PEER3 {
        public String getNome() {
            return "PEER3";
        }
    },
    PEER4 {
        public String getNome() {
            return "PEER4";
        }
    };
    public String getNome(){
        return "NULO";
    }
}
