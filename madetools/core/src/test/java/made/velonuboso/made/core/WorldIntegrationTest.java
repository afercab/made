/*
 * Copyright (C) 2015 rhgarcia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package made.velonuboso.made.core;

import com.velonuboso.made.core.AntMite;
import com.velonuboso.made.core.World;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rhgarcia
 */
public class WorldIntegrationTest {
    
    public WorldIntegrationTest() {
    }
    
    private AntMite antmite;
    private World world;
    
    @Before
    public void setUp() {
        antmite = new AntMite();
        world = new World();
        world.addInhabitant(antmite);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void AntMite_is_alive(){
        world.run(1);
        String events = world.getEventsAsString();
        assertTrue("Should've written CharacterExists", events.contains("InhabitantExists (0, \"Kroo\")"));
    }
    
    
}
