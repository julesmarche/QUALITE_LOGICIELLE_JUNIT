
import camix.service.CanalChat;
import camix.service.ClientChat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

public class CanalChatTestEx1Mockito {

    private ClientChat clientChatMock;
    private CanalChat canalChat = new CanalChat("Canal_de_test");
    @BeforeEach
    public void setUp() throws Exception {
        this.clientChatMock = Mockito.mock(ClientChat.class);
    }

    @Test
    public void ajouteClientTest() {
        Mockito.when(
                this.clientChatMock.donneId()
        ).thenReturn(
            "10"
        );
        canalChat.ajouteClient(clientChatMock);
        assertTrue(canalChat.estPresent(clientChatMock));
        assertEquals(
                (int)(canalChat.donneNombreClients()),
                (int)(1)
        );
        Mockito.verify(this.clientChatMock, Mockito.times(3)).donneId();
        Mockito.verifyNoMoreInteractions(this.clientChatMock);
    }

    @Test
    public void ajouteClientDejaPresentTest() {
        Mockito.when(
                this.clientChatMock.donneId()
        ).thenReturn(
                "10"
        );

        canalChat.ajouteClient(clientChatMock);
        canalChat.ajouteClient(clientChatMock);

        assertEquals(
                (int)(canalChat.donneNombreClients()),
                (int)(1)
        );

        Mockito.verify(this.clientChatMock, Mockito.times(3)).donneId();
        Mockito.verifyNoMoreInteractions(this.clientChatMock);
    }


    @Test
    public void ajouteClientTestVersion2() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(
                this.clientChatMock.donneId()
        ).thenReturn(
                "10"
        );

        Field hashTableClients = CanalChat.class.getDeclaredField("clients");
        hashTableClients.setAccessible(true);
        Hashtable<String, ClientChat> clients =  (Hashtable<String, ClientChat>) hashTableClients.get(canalChat);

        canalChat.ajouteClient(clientChatMock);

        assertEquals(clients.size(), 1);
        assertTrue(clients.contains(clientChatMock));

        Mockito.verify(this.clientChat, Mockito.times(2)).donneId();
    }

    @Test
    public void ajouteClientDejaPresentTestVersion2() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(
                this.clientChatMock.donneId()
        ).thenReturn(
                "10"
        );

        Field hashTableClients = CanalChat.class.getDeclaredField("clients");
        hashTableClients.setAccessible(true);
        Hashtable<String, ClientChat> clients =  (Hashtable<String, ClientChat>) hashTableClients.get(canalChat);

        canalChat.ajouteClient(clientChatMock);
        canalChat.ajouteClient(clientChatMock);

        assertEquals(clients.size(), 1);
        assertTrue(clients.contains(clientChatMock));

        Mockito.verify(this.clientChat, Mockito.times(2)).donneId();
    }

    @Test
    public void ajouteClientTestVersion2_1() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(
                this.clientChatMock.donneId()
        ).thenReturn(
                "10"
        );

        Field hashTableClients = CanalChat.class.getDeclaredField("clients");
        hashTableClients.setAccessible(true);

        Hashtable<String, ClientChat> clients =  (Hashtable<String, ClientChat>) hashTableClients.get(canalChat);

        // Précondition
        clients.put("10",this.clientChatMock);

        // Test
        canalChat.ajouteClient(clientChatMock);

        assertEquals(clients.size(), 1);
        assertTrue(clients.contains(clientChatMock));

        Mockito.verify(this.clientChat, Mockito.times(2)).donneId();
    }

    @Test
    public void ajouteClientTestVersion3() throws NoSuchFieldException, IllegalAccessException {
        // Création du mock ClientChat
        ClientChat clientChatMock = Mockito.mock(ClientChat.class);
        Mockito.when(clientChatMock.donneId()).thenReturn("10");

        // Création du mock partiel de CanalChat avec substitution de la méthode estPresent(c)
        CanalChat canalChatSpy = Mockito.spy(new CanalChat(""));
        Mockito.doReturn(false).when(canalChatSpy).estPresent(clientChatMock);

        // Récupération de la table des clients en utilisant l'introspection
        Field hashTableClients = CanalChat.class.getDeclaredField("clients");
        hashTableClients.setAccessible(true);
        Hashtable<String, ClientChat> clients =  (Hashtable<String, ClientChat>) hashTableClients.get(canalChatSpy);

        // Appel de la méthode à tester
        canalChatSpy.ajouteClient(clientChatMock);

        // Validation des effets de bord
        assertEquals(clients.size(), 1);
        assertTrue(clients.contains(clientChatMock));

        // Vérification des sollicitations faites au mock
        Mockito.verify(canalChatSpy, Mockito.times(1)).estPresent(clientChatMock);
    }


    @Test
    public void ajouteClientTestDejaPresentVersion3() throws NoSuchFieldException, IllegalAccessException {
        // Création du mock ClientChat
        ClientChat clientChatMock = Mockito.mock(ClientChat.class);

        // Création du mock partiel de CanalChat avec substitution de la méthode estPresent(c)
        CanalChat canalChatSpy = Mockito.spy(new CanalChat(""));
        Mockito.doReturn(true).when(canalChatSpy).estPresent(clientChatMock);

        // Récupération de la table des clients en utilisant l'introspection
        Field hashTableClients = CanalChat.class.getDeclaredField("clients");
        hashTableClients.setAccessible(true);
        Hashtable<String, ClientChat> clients =  (Hashtable<String, ClientChat>) hashTableClients.get(canalChatSpy);

        clients.put("10", clientChatMock);

        // Appel de la méthode à tester
        canalChatSpy.ajouteClient(clientChatMock);

        // Validation des effets de bord
        assertEquals(clients.size(), 1);
        assertTrue(clients.contains(clientChatMock));

        // Vérification des sollicitations faites au mock (aucune dans  ce cas là)
        Mockito.verifyNoInteractions(clientChatMock);
    }


}
