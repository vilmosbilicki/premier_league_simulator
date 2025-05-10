package Nagyhazi;

import static org.junit.jupiter.api.Assertions.*;
class TeamTest {

    @org.junit.jupiter.api.Test
    void testEquals() {
        Team team1 = new Team("myteam");
        Team team2 = new Team("myteam");
        assertTrue(team1.equals(team2));
    }

    @org.junit.jupiter.api.Test
    void testGetSport() {
        Team team = new Team("myteam");
        String sport = team.getSport();
        assertEquals("sport", sport);
    }

    @org.junit.jupiter.api.Test
    void testGetName() {
        Team team = new Team("myteam");
        String name = team.getName();
        assertEquals("myteam", name);
    }

    @org.junit.jupiter.api.Test
    void testSetStarters() {
        Team team = new Team("myteam");
        team.setStarters(11);
        assertEquals(11, team.getStarters());
    }

    @org.junit.jupiter.api.Test
    void testSetMaxPlayers() {
        Team team = new Team("myteam");
        team.setMaxPlayers(11);
        assertEquals(11, team.getMaxPlayers());
    }

    @org.junit.jupiter.api.Test
    void testSetName() {
        Team team = new Team("myteam");
        team.setName("myteam2");
        assertEquals("myteam2", team.getName());
    }
}

class PlayerTest {
    @org.junit.jupiter.api.Test
    void testGetName() {
        Player p = new Player("Ronaldo", 99);
        assertEquals("Ronaldo", p.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetRating() {
        Player p = new Player("Ronaldo", 99);
        assertEquals(99, p.getRating());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        Player p1 = new Player("Ronaldo", 99);
        Player p2 = new Player("Ronaldo", 99);
        assertTrue(p1.equals(p2));
    }
}

class ChampionshipTest {
    @org.junit.jupiter.api.Test
    void testGetMaxNumber() {
        Championship championship = new Championship(20);
        assertEquals(20, championship.getMaxNumber());
    }
}