package casodeestudio;

public class TElementoAB<T> implements IElementoAB<T> {

    private final Comparable etiqueta;
    private TElementoAB<T> hijoIzq;
    private TElementoAB<T> hijoDer;
    private final T datos;
    private int altura;

    public TElementoAB(Comparable unaEtiqueta, T unDato) {
        etiqueta = unaEtiqueta;
        hijoIzq = null;
        hijoDer = null;
        datos = unDato;
        altura = 0;
    }

    @Override
    public Comparable getEtiqueta() {
        return etiqueta;
    }

    @Override
    public T getDatos() {
        return datos;
    }

    public int getAltura() {
        return this.altura;
    }

    @Override
    public TElementoAB<T> getHijoIzq() {
        return hijoIzq;
    }

    @Override
    public TElementoAB<T> getHijoDer() {
        return hijoDer;
    }

    @Override
    public void setHijoIzq(TElementoAB<T> elemento) {
        this.hijoIzq = elemento;
    }

    @Override
    public void setHijoDer(TElementoAB<T> elemento) {
        this.hijoDer = elemento;
    }

    @Override
    public TElementoAB<T> buscar(Comparable unaEtiqueta) {
        if (esVacio()) {
            return null;
        } else if (unaEtiqueta.compareTo(etiqueta) == 0) {
            return this;
        } else if (unaEtiqueta.compareTo(etiqueta) < 0 && hijoIzq != null) {
            return hijoIzq.buscar(unaEtiqueta);

        } else if (unaEtiqueta.compareTo(etiqueta) > 0 && hijoDer != null) {
            return hijoDer.buscar(unaEtiqueta);
        } else {
            return null;
        }
    }

    @Override
    public boolean insertar(TElementoAB<T> elemento) {
        if (elemento.getEtiqueta().compareTo(etiqueta) == 0) {
            return false;
        } else if (elemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return hijoIzq.insertar(elemento);
            } else {
                this.hijoIzq = elemento;
                return true;
            }
        } else {
            if (hijoDer != null) {
                return hijoDer.insertar(elemento);
            } else {
                this.hijoDer = elemento;
                return true;
            }
        }
    }

    @Override
    public String preOrden() {
        String res = this.etiqueta + "-";
        if (hijoIzq != null) {
            res += hijoIzq.preOrden();
        }
        if (hijoDer != null) {
            res += hijoDer.preOrden();
        }
        return res;
    }

    @Override
    public String inOrden() {
        String res = "";
        if (hijoIzq != null) {
            res = hijoIzq.inOrden();
        }
        res += this.etiqueta.toString() + ", ";
        if (hijoDer != null) {
            res += hijoDer.inOrden();
        }
        return res;
    }

    @Override
    public String postOrden() {
        String res = "";
        if (hijoIzq != null) {
            res += hijoIzq.postOrden();
        }
        if (hijoDer != null) {
            res += hijoDer.postOrden();
        }
        res += this.etiqueta + "-";
        return res;
    }

    @Override
    public TElementoAB eliminar(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.getEtiqueta()) < 0) {
            if (this.hijoIzq != null) {
                this.hijoIzq = hijoIzq.eliminar(unaEtiqueta);
            }
            return this;
        }
        if (unaEtiqueta.compareTo(this.getEtiqueta()) > 0) {
            if (this.hijoDer != null) {
                this.hijoDer = hijoDer.eliminar(unaEtiqueta);
            }
            return this;
        }
        return quitaElNodo();
    }

    private TElementoAB quitaElNodo() {
        if (hijoIzq == null) {
            return hijoDer;
        }

        if (hijoDer == null) {
            return hijoIzq;
        }

        TElementoAB elHijo = hijoIzq;
        TElementoAB elPadre = this;

        while (elHijo.getHijoDer() != null) {
            elPadre = elHijo;
            elHijo = elHijo.getHijoDer();
        }

        if (elPadre != this) {
            elPadre.setHijoDer(elHijo.getHijoIzq());
            elHijo.setHijoIzq(hijoIzq);
        }

        elHijo.setHijoDer(hijoDer);
        return elHijo;
    }

    public int sumaDeClavesEnNivel(int nivel) {
        if (nivel == 0) {
            return Integer.parseInt(this.etiqueta.toString());
        }
        int res = 0;

        if (this.hijoIzq != null) {
            res += this.hijoIzq.sumaDeClavesEnNivel(nivel - 1);
        }
        if (this.hijoDer != null) {
            res += this.hijoDer.sumaDeClavesEnNivel(nivel - 1);
        }
        return res;

    }

    public boolean esVacio() {
        return etiqueta == null;
    }

    public int altura() {
        int a = -1;
        int b = -1;
        if (hijoIzq != null) {
            a = hijoIzq.altura();
        }
        if (hijoDer != null) {
            b = hijoDer.altura();
        }
        if (a > b) {
            return a + 1;
        } else {
            return b + 1;
        }
    }

    public boolean esHoja() {
        return (hijoIzq == null && hijoDer == null);
    }

    public int cantHojas() {
        if (esHoja()) {
            return 1;
        } else if (hijoDer != null && hijoIzq != null) {
            return hijoDer.cantHojas() + hijoIzq.cantHojas();
        } else if (hijoDer != null) {
            return hijoDer.cantHojas();
        } else {
            return hijoIzq.cantHojas();
        }
    }

    public int tamaño() {
        if (esHoja()) {
            return 1;
        } else if (hijoDer != null && hijoIzq != null) {
            return 1 + hijoDer.tamaño() + hijoIzq.tamaño();
        } else if (hijoIzq != null) {
            return 1 + hijoIzq.tamaño();
        } else {
            return 1 + hijoDer.tamaño();
        }
    }

    public int obtenerNivel(Comparable unaEtiqueta) {
        int nivel = -1;
        if (etiqueta.compareTo(unaEtiqueta) == 0) {
            return 0;
        }
        if (unaEtiqueta.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return 1 + hijoIzq.obtenerNivel(unaEtiqueta);
            }
        }
        if (hijoDer != null) {
            return 1 + hijoDer.obtenerNivel(unaEtiqueta);
        }
        return nivel;
    }

    public Comparable claveInmediata(Comparable clave) {
        if (clave.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                if (this.hijoIzq.getEtiqueta().compareTo(clave) == 0) {
                    return this.getEtiqueta();
                } else {
                    return hijoIzq.claveInmediata(clave);
                }

            }
        } else if (clave.compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                if (this.hijoDer.getEtiqueta().compareTo(clave) == 0) {
                    return this.getEtiqueta();
                } else {
                    return hijoDer.claveInmediata(clave);
                }
            }
        }
        return null;
    }

    public int cantidadDeNodosEnNivel(int nivel) {
        int res = 0;
        if (nivel == 0) {
            res = 1;
        } else {
            if (hijoIzq != null) {
                res += hijoIzq.cantidadDeNodosEnNivel(nivel - 1);
            }
            if (hijoDer != null) {
                res += hijoDer.cantidadDeNodosEnNivel(nivel - 1);
            }
        }
        return res;
    }

    public void listarHojas(Lista<Integer> listaDeHojas, TElementoAB<T> raiz) {
        if (!esVacio()) {
            if (esHoja()) {
                Nodo<Integer> nodo = new Nodo<>(etiqueta, raiz.obtenerNivel(etiqueta));
                listaDeHojas.insertar(nodo);
            } else {
                if (hijoIzq != null) {
                    hijoIzq.listarHojas(listaDeHojas, raiz);
                }
                if (hijoDer != null) {
                    hijoDer.listarHojas(listaDeHojas, raiz);
                }
            }
        }
    }

    public boolean esDeBusqueda() {
        if (hijoIzq != null) {
            if (etiqueta.compareTo(hijoIzq.getEtiqueta()) < 0) {
                return false;
            }
            return hijoIzq.esDeBusqueda();
        }
        if (hijoDer != null) {
            if (etiqueta.compareTo(hijoDer.getEtiqueta()) > 0) {
                return false;
            }
            return hijoDer.esDeBusqueda();
        }
        return true;

    }

    public Comparable<T> menorClave() {
        if (hijoIzq != null) {
            return hijoIzq.menorClave();
        } else {
            return this.getEtiqueta();
        }
    }

    public Comparable<T> mayorClave() {
        if (hijoDer != null) {
            return hijoDer.mayorClave();
        } else {
            return this.getEtiqueta();
        }
    }
    
    //****************************************************************************

    //Metodos de arbol AVL    
    

    /**
     * Inserta claves el arbol balanceando al mismo tiempo .
     *
     * @param unElemento
     * @return
     */
    public TElementoAB<T> insertarBalanceado(TElementoAB unElemento) {
        if (unElemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                hijoIzq = hijoIzq.insertarBalanceado(unElemento);
                return balancearArbol();
            } else {
                hijoIzq = unElemento;
                return this;
            }
        } else if (unElemento.getEtiqueta().compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                hijoDer = hijoDer.insertarBalanceado(unElemento);
                return balancearArbol();
            } else {
                hijoDer = unElemento;
                return this;
            }
        } else {
            // ya existe un elemento con la misma etiqueta.
            return this;
        }
    }

    /**
     * Elimina, balancea de ser necesario y devuelve la nueva Raiz.
     *
     * @param unaEtiqueta
     * @return
     */
    public TElementoAB<T> eliminarBalanceado(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.etiqueta) < 0) {
            if (hijoIzq != null) {
                hijoIzq = hijoIzq.eliminarBalanceado(unaEtiqueta);
                return balancearArbol();
            }
            return this;
        }
        else if (unaEtiqueta.compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                hijoDer = hijoDer.eliminarBalanceado(unaEtiqueta);
                return balancearArbol();
            }
            return this;
        }
        TElementoAB<T> nuevo = quitaElNodo();
        return nuevo;
    }

   
    public void nuevaAltura() {
        int izq = -1;
        int der = -1;
        if (hijoIzq != null) {
            izq = hijoIzq.getAltura();
        }
        if (hijoDer != null) {
            der = hijoDer.getAltura();
        }
        altura = 1 + Math.max(izq, der);
    }

    /**
     * Realiza la rotacion necesaria cuando la causa del desbalance viene del
     * hijo izquierdo del hijo izquierdo.
     *
     * @param k2 El nodo en el que se da el desbalance
     * @return El nodo que toma el lugar de k2.
     */
    private TElementoAB<T> rotacionLL(TElementoAB k2) {
        TElementoAB<T> k1 = k2.getHijoIzq();
        k2.setHijoIzq(k1.getHijoDer());
        k1.setHijoDer(k2);
        k2.nuevaAltura();
        k1.nuevaAltura();
        return k1;
    }

    /**
     * Realiza la rotacion necesaria cuando la causa del desbalance viene del
     * hijo derecho del hijo derecho.
     *
     * @param k1 El nodo en el que se da el desbalance
     * @return El nodo que toma el lugar de k1.
     */
    private TElementoAB<T> rotacionRR(TElementoAB k1) {
        TElementoAB<T> k2 = k1.getHijoDer();
        k1.setHijoDer(k2.getHijoIzq());
        k2.setHijoIzq(k1);
        k1.nuevaAltura();
        k2.nuevaAltura();
        return k2;
    }

    /**
     * Realiza la rotacion necesaria cuando la causa del desbalance viene del
     * hijo derecho del hijo izquierdo.
     *
     * @param k3 El nodo en el que se da el desbalance
     * @return El nodo que toma el lugar de k3.
     */
    private TElementoAB<T> rotacionLR(TElementoAB k3) {
        k3.setHijoIzq(rotacionRR(k3.getHijoIzq()));
        return rotacionLL(k3);
    }

    /**
     * Realiza la rotacion necesaria cuando la causa del desbalance viene del
     * hijo izquierdo del hijo derecho.
     *
     * @param k1 El nodo en el que se da el desbalance
     * @return El nodo que toma el lugar de k1.
     */
    private TElementoAB<T> rotacionRL(TElementoAB k1) {
        k1.setHijoDer(rotacionLL(k1.getHijoDer()));
        return rotacionRR(k1);
    }

    private int diferenciaAltura(TElementoAB elem) {
        int alturaDerecha;
        int alturaIzquierda;
        
        if (elem.getHijoDer() != null) {
            alturaDerecha = elem.getHijoDer().getAltura();
        } else {
            alturaDerecha = -1;
        }

        if (elem.getHijoIzq() != null) {
            alturaIzquierda = elem.getHijoIzq().getAltura();
        } else {
            alturaIzquierda = -1;
        }

        return alturaDerecha - alturaIzquierda;
    }

    /**
     * Realiza una búsqueda sobre el árbol y balancea el mismo de ser necesario
     * .
     *
     * @return Un arbol balanceado.
     */
    private TElementoAB<T> balancearArbol() {
        int diferencia = diferenciaAltura(this);
        
        if (diferencia == -2) {
            if (this.hijoIzq.getHijoIzq().getAltura() > this.hijoIzq.getHijoDer().getAltura()) {
                return rotacionLL(this);
            } else {
                return rotacionLR(this);
            }
        } else if (diferencia == 2) {
            if (this.hijoDer.getHijoDer().getAltura() > this.hijoDer.getHijoIzq().getAltura()) {
                return rotacionRR(this);
            } else {
                return rotacionRL(this);
            }
        }
        return this;
    }
    
    //*******************************************************************************************
    
    public int obtenerAltura() {
        int izq = 0;
        int der = 0;
        if (hijoIzq != null) {
            izq = hijoIzq.obtenerAltura() + 1;
        }
        if (hijoDer != null) {
            der = hijoDer.obtenerAltura() + 1;
        }
        return Integer.max(izq, der);
    }

    public boolean esAVL() {
        boolean esAvl = true;
        int izq = 0;
        int der = 0;
        if (hijoIzq != null) {
            izq = this.obtenerAltura();
        }
        if (hijoDer != null) {
            der = this.obtenerAltura();
        }
        if (Math.abs(izq - der) > 1) {
            return false;
        } else {
            if (hijoIzq != null) {
                esAvl = (esAvl && hijoIzq.esAVL());
            }
            if (hijoDer != null) {
                esAvl = (esAvl && hijoDer.esAVL());
            }
        }
        return esAvl;
    }
}
