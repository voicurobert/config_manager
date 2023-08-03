package ro.dev.ree.cross_config_manager.model.link_type;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LinkTypeServiceUnitTest {

    @Mock
    private LinkTypeService linkTypeService;

    @Test
    public void saveTest() {
        LinkTypeDto linkTypeDto = new LinkTypeDto();

        linkTypeDto.setName("Optical Cable");

        //linkTypeService.save(linkTypeDto);
    }

}
