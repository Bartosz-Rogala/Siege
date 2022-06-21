package server;

import game.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.*;
import java.net.Socket;

public class SessionHandlerTest {
    Socket socket1;
    Socket socket2;

    @BeforeEach
    public void mockData() throws IOException {
        socket1 = Mockito.mock(Socket.class);
        socket2 = Mockito.mock(Socket.class);

        Mockito.when(socket1.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        Mockito.when(socket1.getOutputStream()).thenReturn(new OutputStream() {
            @Override
            public void write(int b) {
            }
        });

        Mockito.when(socket2.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        Mockito.when(socket2.getOutputStream()).thenReturn(new OutputStream() {
            @Override
            public void write(int b) {
            }
        });
    }


    @Test
    public void shouldCreateSessionHandler() throws IOException {
        SessionHandler sessionHandler = new SessionHandler(socket1, socket2);

        Assertions.assertNotNull(sessionHandler);
        Mockito.verify(socket1, Mockito.times(1)).getOutputStream();
    }

    @Test
    public void shouldRunGameAndDoNothingWhenTheresNoMessage() throws IOException {
// given
        Player player = Mockito.mock(Player.class);
        BufferedReader bufferedReader = Mockito.mock(BufferedReader.class);
        PrintWriter printWriter1 = Mockito.mock(PrintWriter.class);
        PrintWriter printWriter2 = Mockito.mock(PrintWriter.class);
//        spr przyp z zawartością chat
        Mockito.when(bufferedReader.readLine()).thenReturn(null);
        Mockito.when(player.getSocket()).thenReturn(socket1);
        SessionHandler sessionHandler = new SessionHandler(socket1, socket2);

        Mockito.reset(socket1);
        Mockito.reset(socket2);
//        when
        sessionHandler.doRun(player, bufferedReader, printWriter1, printWriter2);
//        then
        Mockito.verify(socket1, Mockito.times(0)).getPort();
        Mockito.verify(socket2, Mockito.times(0)).getPort();
        Mockito.verify(socket1, Mockito.times(1)).close();
//        Mockito.verify(socket2, Mockito.times(1)).close();
//        Mockito.verify(, Mockito.times(1)).close();
//        Mockito.verify(socket1, Mockito.times(1)).close();
//        Mockito.verify(socket1, Mockito.times(1)).close();
    }

    public void shoulPrintMessageOnChat() {

    }

    public void shouldGenerateArmyForFirstPlayer() {

    }
    public void shouldCountTurns() {

    }

    public void shouldEndgame() {

    }

    public void shouldContinueGame() {

    }

    public void shouldCloseBuffersWhenExceptionOccurs() {

    }
}
