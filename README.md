# Trabalho prático 1 - Grafos

Implementação dos cálculos estátisticos Eccentricity e Closeness Centrality na linguagem de programação Java.

## Eccentricity
Excentricidade é maior distância geodésica (caminho mais curto) de v para qualquer outro vértice no grafo conectado

Implementação em java:

´´´
    public void calcularExcentricidade(Graph G){
       // Excentricidade é maior distância geodésica (caminho mais curto)
      //  de v para qualquer outro vértice no grafo conectado

      //Escrever um arquivo .txt com as saidas
        try (PrintWriter writer = new PrintWriter(new FileWriter("eccentricity.txt"))) {
            // Para cada vértice 'v' no grafo
            for (int v = 0; v < G.V(); v++) {
                // Executar a BFS a partir de 'v' para obter todas as distâncias
                int[] distancias = G.bfs(v);

                // Encontra a maior distância entre todas as distâncias calculadas
                int maxDistancia = 0;
                boolean isInComponent = false;

                for (int i = 0; i < G.V(); i++) {
                    // Considera apenas vértices alcançáveis (distancia != -1)
                    if (distancias[i] != -1) {
                        if (distancias[i] > maxDistancia) {
                            maxDistancia = distancias[i];
                        }
                        if (i != v) {
                            isInComponent = true;
                        }
                    }
                }

                String linhaSaida;
                // Formata a string de saída conforme sua requisição
                if (!isInComponent && G.adj(v).iterator().hasNext() == false) {
                     linhaSaida = "Excentricidade do Vértice " + v + ": 0 (isolado)";
                }
                else {
                    linhaSaida = "Excentricidade do Vértice " + v + ": " + maxDistancia;
                }

                System.out.println(linhaSaida); // Imprimir no terminal
                writer.println(linhaSaida);     // Escreve no arquivo
            }
            System.out.println("\n");
            System.out.println("=========================================");
            System.out.println("\n");

        } catch (Exception e) {
            System.err.println("Erro ao escrever no arquivo de excentricidade: " + e.getMessage());
            e.printStackTrace();
        }
    }
´´´

## Closeness Centrality
Representa o quão perto um vértice está de todos os outros vértices no grafo.
Um valor alto indica que o vértice está a uma curta distância de muitos outros, podendo alcançar e ser alcançado rapidamente.

Implementação em java:

´´´
    public void calcularClosenessCentrality(Graph G) {
        /*Representa o quão perto um vértice está de todos os outros vértices no grafo.
        Um valor alto indica que o vértice está a uma curta distância de muitos outros,
        podendo alcançar e ser alcançado rapidamente. */
    
      try (PrintWriter writer = new PrintWriter(new FileWriter("closeness_centrality.txt"))) {

        for (int v = 0; v < G.V(); v++) {
            int[] distancias = G.bfs(v); // Obtém as distâncias de 'v' para todos os outros
            double somaDasDistancias = 0.0;
            int numVerticesAtingiveis = 0; // Quantos vértices 'v' consegue alcançar (incluindo ele mesmo)

            for (int i = 0; i < G.V(); i++) {
                if (distancias[i] != -1) { // Se o vértice 'i' é alcançável a partir de 'v'
                    somaDasDistancias += distancias[i];
                    numVerticesAtingiveis++;
                }
            }

            double closenessCentralityNormalizada;
            String linhaSaida;

            // Tratamento de casos especiais para o cálculo da Closeness Centrality
            if (numVerticesAtingiveis == 1 && G.V() > 1) { // Vértice isolado em um grafo com mais de um vértice
                closenessCentralityNormalizada = 0.0;
                linhaSaida = String.format("Closeness Centrality Normalizada do Vértice %d: %.4f (isolado)%n", v, closenessCentralityNormalizada);
            } else if (somaDasDistancias == 0 && G.V() == 1) { // Grafo de um único vértice
                closenessCentralityNormalizada = 1.0;
                linhaSaida = String.format("Closeness Centrality Normalizada do Vértice %d: %.4f (grafo de um único vértice)%n", v, closenessCentralityNormalizada);
            } else if (somaDasDistancias == 0 && G.V() > 1) { // Vértice que só se alcança em grafo maior (não deveria acontecer se numVerticesAtingiveis > 1)
                closenessCentralityNormalizada = 0.0;
                linhaSaida = String.format("Closeness Centrality Normalizada do Vértice %d: %.4f (não alcança outros vértices)%n", v, closenessCentralityNormalizada);
            }
            else {
                if (numVerticesAtingiveis > 1) {
                    closenessCentralityNormalizada = (double) (numVerticesAtingiveis - 1) / somaDasDistancias;
                } else {
                    closenessCentralityNormalizada = 0.0; // Caso de segurança para vértice isolado
                }
                linhaSaida = String.format("Closeness Centrality Normalizada do Vértice %d: %.4f%n", v, closenessCentralityNormalizada);
            }
            System.out.print(linhaSaida);  // Imprimir no terminal
            writer.print(linhaSaida);      // Escrever no arquivo
        }
            System.out.println("\n");
            System.out.println("=========================================");
            System.out.println("\n");

      } catch (Exception e) {
        System.err.println("Erro ao escrever no arquivo de closeness centrality: " + e.getMessage());
        e.printStackTrace();
    }
  }
´´´
## Como usar ?
Para usar os algoritimos deve primeiro clonar o repositorio
´´´

´´´
O arquivo que tem os grafos está em 'LesMiserables.gexf' com os vertices e o numeor de grafos.
Depois deve executar o comando Para compilar

´´´

javac ReadXMLFile.java 

´´´

Por fim execute o comando par executar

´´´

java ReadXMLFile

´´´

No terminal irá exibir o Closeness Centrality normalizado de cada vértice e a excentricidade de cada vértice
E irá colocar essas saídas em um arquivo txt.