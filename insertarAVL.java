public int insertarAVL(TElementoAB<T> elemento, TElementoAB[] actual, boolean[] avl, boolean[] inserto) {
        int a = -1;
        int b = -1;

        if (elemento.getEtiqueta().compareTo(etiqueta) == 0) {
            inserto[0] = false;
        } 
        else if (elemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                a = hijoIzq.insertarAVL(elemento, actual, avl, inserto);
                if (!avl[0]) {      //O(1)
                    hijoIzq = actual[0];    //O(1)
                    avl[0] = true;       //O(1)
                    a--;
                }
            } 
            else {
                this.hijoIzq = elemento;
            }
        } 
        else {
            if (hijoDer != null) {
                b = hijoDer.insertarAVL(elemento, actual, avl, inserto);
                if (!avl[0]) {      //O(1)
                    hijoDer = actual[0];        //O(1)
                    avl[0] = true;           //O(1)
                    b--;
                }

 

            } 
            else {
                this.hijoDer = elemento;
            }
        }

        if (inserto[0]) { 

            int balance = (a - b);          //O(1)
            if (balance > 1 && elemento.getEtiqueta().compareTo(hijoIzq.getEtiqueta()) < 0) {     //O(1)
                actual[0] = this.rotacionConHijoIzq();          //O()
                avl[0] = false;          //O(1)
            } 
            else if (balance > 1 && elemento.getEtiqueta().compareTo(hijoIzq.getEtiqueta()) > 0) {      //O(1)
                actual[0] = this.rotacionDobleConHijoIzq();     //O()
                avl[0] = false;      //O(1)
            } 
            else if (balance < -1 && elemento.getEtiqueta().compareTo(hijoDer.getEtiqueta()) > 0) {     //O(1)
                actual[0] = this.rotacionConHijoDer();          //O()
                avl[0] = false;          //O(1)
            } 
            else if (balance < -1 && elemento.getEtiqueta().compareTo(hijoDer.getEtiqueta()) < 0) {     //O(1)
                actual[0] = this.rotacionDobleConHijoDer();         //O()
                avl[0] = false;          //O(1)
            }
        }
        return Math.max(a + 1, b + 1);
    }