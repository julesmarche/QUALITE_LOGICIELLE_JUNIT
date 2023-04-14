import camix.communication.ProtocoleChat;
import camix.service.CanalChat;
import camix.service.ClientChat;
import camix.service.ServiceChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ServiceChatTestEx2Mockito {
    @Mock
    private ClientChat clientChat;

    private ServiceChat serviceChat;

    @BeforeEach
    public void setUp() throws IOException {
        this.serviceChat = new ServiceChat("testCanal");
    }

    @Test
    public void testInformeDepartClient() throws IOException {
        Mockito.when(this.clientChat.donneSurnom()).thenReturn("Exemple");

        this.serviceChat.ajouteCanal(clientChat, "Exemple");
        this.serviceChat.informeDepartClient(clientChat);

        Mockito.verify(this.clientChat, Mockito.times(1)).envoieMessage(String.format(ProtocoleChat.MESSAGE_CREATION_CANAL, "Exemple"));
        
        Mockito.verify(this.clientChat, Mockito.times(1)).envoieContacts(String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, "Exemple"));
         
        Mockito.verify(this.clientChat, Mockito.times(1)).envoieMessage(String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, "Exemple"));
        
        Mockito.verify(this.clientChat, Mockito.times(1)).donneSurnom();
    }

    @Test
    public void testInformeDepartClientPasClient() throws IOException {
        Mockito.when(this.clientChat.donneSurnom()).thenReturn("Exemple");

        this.serviceChat.informeDepartClient(this.clientChat);
 
        Mockito.verify(this.clientChat, Mockito.times(0)).envoieMessage(String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, "Exemple"));
        
        Mockito.verify(this.clientChat, Mockito.times(1)).donneSurnom();
    }

}
