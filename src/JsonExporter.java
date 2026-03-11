import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonExporter {

    public static void export(Gameplay gameplay, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(toJson(gameplay));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file: " + filename, e);
        }
    }

    public static String toJson(Gameplay gameplay) {
        StringBuilder sb = new StringBuilder();
        Board board = gameplay.getBoard();
        List<Player> players = gameplay.getPlayers();

        sb.append("{\n");

        sb.append("  \"turn\": ").append(gameplay.getTurn()).append(",\n");

        sb.append("  \"players\": [\n");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            sb.append("    {\n");
            sb.append("      \"playerNumber\": ").append(p.getPlayerNumber()).append(",\n");

            sb.append("      \"resources\": ").append(resourceMapToJson(p.getResources())).append(",\n");

            sb.append("      \"settlements\": ").append(nodesToJson(p.getSettlements())).append(",\n");
            sb.append("      \"cities\": ").append(nodesToJson(p.getCities())).append(",\n");
            sb.append("      \"roads\": ").append(roadsToJson(p.getRoads())).append("\n");

            sb.append("    }");
            if (i < players.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("  ],\n");

        sb.append("  \"board\": {\n");
        sb.append("    \"tiles\": ").append(tilesToJson(board.getTiles())).append(",\n");
        sb.append("    \"nodes\": ").append(boardNodesToJson(board)).append("\n");
        sb.append("  }\n");

        sb.append("}\n");

        return sb.toString();
    }

    private static String resourceMapToJson(Map<Resource, Integer> resources) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int count = 0;
        for (Resource r : Resource.values()) {
            if (count > 0) sb.append(", ");
            Integer value = resources.get(r);
            if (value == null) value = 0;
            sb.append("\"").append(r.name()).append("\": ").append(value);
            count++;
        }
        sb.append("}");
        return sb.toString();
    }

    private static String nodesToJson(List<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            sb.append(nodeToJson(node));
            if (i < nodes.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String nodeToJson(Node node) {
        return "{"
                + "\"id\": " + node.getNodeId()
                + "}";
    }

    private static String roadsToJson(List<Road> roads) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            sb.append("{")
              .append("\"from\": ").append(road.getNodeOne().getNodeId()).append(", ")
              .append("\"to\": ").append(road.getNodeTwo().getNodeId())
              .append("}");
            if (i < roads.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String tilesToJson(Tile[] tiles) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tiles.length; i++) {
            Tile tile = tiles[i];
            sb.append("{")
              .append("\"id\": ").append(i).append(", ")
              .append("\"resource\": \"").append(tile.getResource().name()).append("\", ")
              .append("\"number\": ").append(tile.getValue())
              .append("}");
            if (i < tiles.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private static String boardNodesToJson(Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < 54; i++) {
            Node node = board.getNodes(i);
            sb.append(nodeToJson(node));
            if (i < 53) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}