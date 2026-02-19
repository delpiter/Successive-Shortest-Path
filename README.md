# Successive Shortest Path Algorithm

Implementazione dell'algoritmo **Successive Shortest Path** per la risoluzione del problema di flusso a costo minimo (Minimum Cost Flow Problem).

## Descrizione

L'algoritmo ***Successive Shortest Path*** (`SSP`) risolve il problema di flusso a costo minimo in una rete di trasporto, trovando il modo più economico per instradare il flusso da nodi sorgente (con *disponibilità*) a nodi pozzo (con *richieste*), rispettando i vincoli di capacità degli archi.

### Problema di Flusso a Costo Minimo

Dato un grafo diretto $G=(N,A)$ dove:
- Ogni nodo $i\in N$ ha uno sbilanciamento $b_{i}$ (**supply** se positivo, **demand** se negativo).
- Ogni arco $(i,j)\in A$ ha un costo unitario $c_{ij}$ e una capacità $u_{ij}$.
- Vale il vincolo di bilanciamento: <br>
$\displaystyle\sum_{(i\in N)} b_{i}= 0$

L'obiettivo è trovare un flusso $x$ che ***minimizzi il costo totale***: <br>
$\min z = \displaystyle\sum_{(i,j)\in A} c_{ij} \cdot x_{ij}$ <br>
rispettando:
- Vincoli di capacità: $0\leq x_{ij} \leq u_{ij}$.
- Vincoli di conservazione del flusso in ogni nodo.

## Funzionamento dell'Algoritmo

L'algoritmo opera iterativamente:
1. **Inizializzazione**: Flusso zero, potenziali zero, calcola gli insiemi $E$ (nodi con eccesso) e $D$ (nodi con deficit).

2. **Iterazione principale** (finché esistono nodi con eccesso):
   - Seleziona una sorgente $k\in E$ e un pozzo $l\in D$.
   - Calcola il cammino di costo minimo da $k$ a $l$ usando **Dijkstra con costi ridotti**.
   - Invia il massimo flusso possibile lungo questo cammino.
   - Aggiorna il grafo residuo e i potenziali.

3. **Costi Ridotti**: Per gestire gli archi con costo negativo nel grafo residuo, l'algoritmo utilizza i costi ridotti: <br>
$c_{ij}^{\pi} = c_{ij} - \pi_{i} + \pi_{j}$ <br>
dove $\pi$ sono i ***potenziali dei nodi***, aggiornati ad ogni iterazione per mantenere i *costi ridotti non-negativi*.
## Formato di Input

Il file di input deve seguire questo formato:
```
nNodes
nodeId,nodeBalance
...
nodeId-[nodeId=(cost,capacity);...]
```

### Esempio
```
6
1,4
2,2
3,0
4,0
5,-2
6,-4
1-[3=(3,7)]
2-[1=(3,1);3=(2,2);4=(6,6)]
3-[6=(5,6);5=(4,8)]
4-[3=(4,3);6=(6,7)]
5-[6=(1,3)]
6-[]
```

Dove:
- Prima riga: **numero di nodi**
- Righe successive: `nodeId, balance` (positivo = supply, negativo = demand, 0 = transit).
- Lista di adiacenza: `sourceNode-[targetNode=(cost,capacity);...]`.

## Complessità

- **Complessità temporale**: $O(U\cdot (m + n\log(n)))$ dove:
  - $U$ = flusso totale da instradare
  - $m$ = numero di archi
  - $n$ = numero di nodi
  - Il termine $(m + n\log(n))$ deriva dall'esecuzione di Dijkstra

>[!note]
>Algoritmo pseudo-polinomiale (dipende dal valore di $U$, non solo dalla dimensione del grafo)

## Struttura del Progetto
```
.
├── README.md
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── graphTheory
│           │       ├── App.java
│           │       ├── graph
│           │       │   ├── Edge.java
│           │       │   ├── Graph.java
│           │       │   └── Node.java
│           │       ├── reader
│           │       │   └── Reader.java
│           │       ├── shortestPath
│           │       │   └── ShortestPathAlgorithm.java
│           │       └── successiveShortestPath
│           │           └── SuccessiveShortestPath.java
│           └── resources
│               ├── input1.in
│               └── inputFail1.in
├── gradlew
└── settings.gradle.kts
```

## Utilizzo
```bash
# Esecuzione
./gradlew run
```

## Riferimenti

- **Algoritmo**: Successive Shortest Path (Busacker-Gowen, 1961)
- **Tecnica**: Johnson's reweighting per costi ridotti
- **Applicazioni**: Logistica, reti di trasporto, assegnamento, scheduling

## Note Implementative
A differenza di come descritto in precedenza, l'algoritmo implementato ***non definisce un insieme di nodi*** $D$.

> Di conseguenza durante l'iterazione, non viene scelto un nodo destinazione $l\in D$, ma solamente un nodo di partenza per il flusso, il nodo di destinazione viene scelto una volta calcolato l'albero dei cammini minimi, scegliendo il nodo finale che *ammette un flusso più alto*.
