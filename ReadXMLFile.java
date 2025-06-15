import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {

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

    public static void main(String[] args) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Graph graph = null;

        try {
            File file = new File("LesMiserables.gexf");
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Total de vértices
            NodeList verticesNodeList = doc.getElementsByTagName("nodes");
            Node verticesNode = verticesNodeList.item(0);
            Element verticesElement = (Element) verticesNode;
            String countVerticesStr = verticesElement.getAttribute("count");
            int totalDeVertices = (int) Double.parseDouble(countVerticesStr);
            System.out.println("Total de Vértices: " + totalDeVertices);

            // Total de arestas
            NodeList arestasNodeList = doc.getElementsByTagName("edges");
            Node arestasNode = arestasNodeList.item(0);
            Element arestasElement = (Element) arestasNode;
            String countArestasStr = arestasElement.getAttribute("count");
            int totalDeArestas = (int) Double.parseDouble(countArestasStr);
            System.out.println("Total de Arestas: " + totalDeArestas);

            // Instanciar o grafo
            graph = new Graph(totalDeVertices);

            // Adicionar as arestas ao grafo
            NodeList edgeList = doc.getElementsByTagName("edge");

            for (int i = 0; i < edgeList.getLength(); i++){
                Node node = edgeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element edge = (Element) node;
                    String source = edge.getAttribute("source");
                    String target = edge.getAttribute("target");

                    int sourceVertex = (int) Double.parseDouble(source);
                    int targetVertex = (int) Double.parseDouble(target);
                    graph.addEdge(sourceVertex, targetVertex);
                }
            }

            // Chamar as funções de cálculo e salvamento em arquivo
            ReadXMLFile reader = new ReadXMLFile();
            if (graph.V() > 0) {
                reader.calcularExcentricidade(graph);
                reader.calcularClosenessCentrality(graph);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falhou ao ler o arquivo XML e construir o grafo!");
        }
    }
}