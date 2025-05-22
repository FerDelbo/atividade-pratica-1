import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {

  
    public static void calcularExentricidade(){
      // Exntricidade é maior distância geodésica (caminho mais curto)
      //  de v para qualquer outro vértice no grafo conectado

    } 
    public static void main(String[] args) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Graph graph = null; // Declare o grafo fora do try para que possa ser acessado

        try {
            File file = new File("LesMiserables.gexf");
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            NodeList listaDeNo = doc.getElementsByTagName("edge");
            //total de vertices 
            NodeList qtdNo = doc.getElementsByTagName("nodes");
            Node nodes = qtdNo.item(0);
            Element count = (Element) nodes;
            String countt = count.getAttribute("count");
            System.out.println(countt);
           // total de arestas 
           NodeList arestasLista = doc.getElementsByTagName("edges");
           Node aresta = arestasLista.item(0);
           Element countAresta = (Element) aresta;
           String countA = countAresta.getAttribute("count");
           System.out.println(countA);
            
            for (int i = 0; i < listaDeNo.getLength(); i++){ 
              Node node = listaDeNo.item(i);
              if (node.getNodeType() == Node.ELEMENT_NODE) {
                  Element edge = (Element) node;
                  String source = edge.getAttribute("source");
                  String target = edge.getAttribute("target");
                  System.out.println("Source " + source + " : " + "Target" + target);
                  // System.out.println("Person " + id + ":" + tagName + ":" + personName);
              }
          } 
            // // --- Primeira Passagem: Coletar todos os nós únicos para determinar o número de vértices ---
            // Set<String> uniqueNodes = new HashSet<>();
            // for (int i = 0; i < edgeList.getLength(); i++) {
            //     Node node = edgeList.item(i);
            //     if (node.getNodeType() == Node.ELEMENT_NODE) {
            //         Element edge = (Element) node;
            //         String source = edge.getAttribute("source");
            //         String target = edge.getAttribute("target");
            //         uniqueNodes.add(source);
            //         uniqueNodes.add(target);
            //     }
            // }

            // int numberOfVertices = uniqueNodes.size();
            // graph = new Graph(numberOfVertices); // Crie a instância do grafo

            // // --- Mapeamento de String de ID para Inteiro de Vértice ---
            // Map<String, Integer> nodeMap = new HashMap<>();
            // int vertexIdCounter = 0;
            // for (String nodeName : uniqueNodes) {
            //     nodeMap.put(nodeName, vertexIdCounter++);
            // }

            // // System.out.println("Número total de vértices (personagens): " + numberOfVertices);
            // System.out.println("Mapeamento de nós para IDs de vértices:");
            // for (Map.Entry<String, Integer> entry : nodeMap.entrySet()) {
            //     System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
            // }

            // // --- Segunda Passagem: Adicionar arestas ao grafo ---
            // System.out.println("\nAdicionando arestas ao grafo:");
            // for (int i = 0; i < edgeList.getLength(); i++) {
            //     Node node = edgeList.item(i);
            //     if (node.getNodeType() == Node.ELEMENT_NODE) {
            //         Element edge = (Element) node;
            //         String sourceId = edge.getAttribute("source");
            //         String targetId = edge.getAttribute("target");

            //         int sourceVertex = nodeMap.get(sourceId);
            //         int targetVertex = nodeMap.get(targetId);

            //         graph.addEdge(sourceVertex, targetVertex);
            //         System.out.println("Aresta adicionada: " + sourceId + " (" + sourceVertex + ") - " + targetId + " (" + targetVertex + ")");
            //     }
            // }

            // System.out.println("\nGrafo construído com sucesso!");
            // System.out.println("Número de vértices no grafo: " + graph.V());
            // System.out.println("Número de arestas no grafo: " + graph.E());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Falhou!");
        }
    }
}

// import java.io.File;
// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

// public class ReadXMLFile {
 
//     public static void main(String[] args) {
   
//       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       
//       try {
       
//         File file                  = new File("LesMiserables.gexf");

//         DocumentBuilder db         = dbf.newDocumentBuilder();
//         Document doc               = db.parse(file);
//         // NodeList personList        = doc.getElementsByTagName("nodes");

//         NodeList listaDeNo = doc.getElementsByTagName("edge");
        
        
//         for (int i = 0; i < listaDeNo.getLength(); i++){
            
//             Node node = listaDeNo.item(i);
//             if (node.getNodeType() == Node.ELEMENT_NODE) {
//                 Element edge = (Element) node;
//                 String source = edge.getAttribute("source");
//                 String target = edge.getAttribute("target");
//                 System.out.println("Source " + source + ":" + " target"+ ":" + target);
//             }
//         } 
//     }
//     catch (Exception e) {
//       e.printStackTrace();
//       System.out.println("Falhou!");
//     }
//   }
// }