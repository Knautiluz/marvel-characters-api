package net.knautiluz.marvel.controllers.dextra.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@SuppressWarnings("unused")
@RestController
public class IndexController {
    @GetMapping(value = "/", produces = "text/plain")
    @ResponseStatus(code = HttpStatus.OK)
    public String getIndex() {
        return "Kon'nichiwa Worudo";
    }

}
