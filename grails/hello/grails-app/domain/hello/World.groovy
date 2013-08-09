package hello

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(["errors", "properties", "attached", "version"])
class World {
    Integer randomNumber

    static constraints = {
    }

    static mapping = {
      version false
      columns {
        randomNumber     column:"randomNumber"
      }
    }
}
