import com.financial.cleaningrobot.api.Direction;
import com.financial.cleaningrobot.api.Position;
import com.financial.cleaningrobot.api.RobotApi;
import com.financial.cleaningrobot.api.RobotControl;
import java.util.List;

/**
 * Robot controller implementation
 */
public class ChallengeRobot {

    /**
     * The main entry point.
     * Don't change the code, besides the file path of the room layout.
     */
    public static void main(String[] args) throws Exception {
        RobotControl rc = new RobotControl("../resources/room-layout-1.txt");
        RobotApi api = rc.getRobotApi();
        cleanRooms(api);
        rc.evaluateResult();
    }

    /**
     * Add your implementation here.
     */
    private static void cleanRooms(RobotApi api) {
        cleanRoomDFS(api, new boolean[1000][1000]);
    }

    private static void cleanRoomDFS(RobotApi robot, boolean[][] visited) {
        // Base case: If the current cell has been visited, return
        if (visited[robot.getPosition().x()][robot.getPosition().y()]) {
            return;
        }

        // Clean the current cell
        robot.move();
        visited[robot.getPosition().x()][robot.getPosition().y()] = true;

        // Explore all four directions
        for (int i = 0; i < 4; i++) {
            if (!robot.isBarrierAhead()) {
                cleanRoomDFS(robot, visited);
            }
            robot.turnRight();  // Turn 90 degrees to the right for the next direction
        }

        // Go back to the original direction
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }
}

class CleaningRobot implements RobotApi {
    private final char[][] room;
    private int x;
    private int y;
    private Direction direction;

    public CleaningRobot(List<String> roomLayout) {
        int rows = roomLayout.size();
        int cols = roomLayout.get(0).length();
        room = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            room[i] = roomLayout.get(i).toCharArray();
            for (int j = 0; j < cols; j++) {
                if (room[i][j] == 'R') {
                    x = i;
                    y = j;
                }
            }
        }

        direction = Direction.UP;  // Assuming the robot starts facing up
    }

    @Override
    public void move() {
        if (!isBarrierAhead()) {
            switch (direction) {
                case UP:
                    x--;
                    break;
                case RIGHT:
                    y++;
                    break;
                case DOWN:
                    x++;
                    break;
                case LEFT:
                    y--;
                    break;
            }
        }
    }

    @Override
    public void turnLeft() {
        switch (direction) {
            case UP:
                direction = Direction.LEFT;
                break;
            case RIGHT:
                direction = Direction.UP;
                break;
            case DOWN:
                direction = Direction.RIGHT;
                break;
            case LEFT:
                direction = Direction.DOWN;
                break;
        }
    }

    @Override
    public void turnRight() {
        switch (direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
        }
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean isBarrierAhead() {
        Position nextPosition = getPositionAhead();
        return nextPosition.x() < 0 || nextPosition.x() >= room.length ||
                nextPosition.y() < 0 || nextPosition.y() >= room[0].length ||
                room[nextPosition.x()][nextPosition.y()] == 'X';
    }

    @Override
    public Position getPosition() {
        return new Position(x, y);
    }

    @Override
    public Position getPositionAhead() {
        Position currentPosition = getPosition();
        switch (direction) {
            case UP:
                return new Position(currentPosition.x() - 1, currentPosition.y());
            case RIGHT:
                return new Position(currentPosition.x(), currentPosition.y() + 1);
            case DOWN:
                return new Position(currentPosition.x() + 1, currentPosition.y());
            case LEFT:
                return new Position(currentPosition.x(), currentPosition.y() - 1);
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }
}
