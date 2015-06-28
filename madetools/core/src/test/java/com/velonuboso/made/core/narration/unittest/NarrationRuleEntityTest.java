/*
 * Copyright (C) 2015 Rubén Héctor García (raiben@gmail.com)
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
package com.velonuboso.made.core.narration.unittest;

import com.velonuboso.made.core.common.unittest.*;
import com.google.common.base.Defaults;
import com.velonuboso.made.core.common.entity.EventsLog;
import com.velonuboso.made.core.common.entity.EventsLogConverter;
import com.velonuboso.made.core.customization.entity.NarrationRuleEntity;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rubén Héctor García (raiben@gmail.com)
 */
public class NarrationRuleEntityTest {

    private NarrationRuleEntity narrationRuleEntity;
    

    @Test
    public void UT_NarrationRuleEntity_getters_must_return_values_from_constructor() throws Exception {
        
        final String SAMPLE_PREDICATE_NATURAL_LANGUAGE_TEMPLATE = "arg";
        final int SAMPLE_PREDICATE_NUMBER_OF_ARGUMENTS = 1;
        final String SAMPLE_PREDICATE_NAME = "sample";

        narrationRuleEntity = new NarrationRuleEntity(SAMPLE_PREDICATE_NAME, 
                SAMPLE_PREDICATE_NUMBER_OF_ARGUMENTS, SAMPLE_PREDICATE_NATURAL_LANGUAGE_TEMPLATE);
        
        assertEquals("Predicate name should've been the one provided in the constructor",
                SAMPLE_PREDICATE_NAME, narrationRuleEntity.getPredicateName());
        
        assertEquals("Predicate number of arguments should've been the one provided in the constructor",
                new Integer(SAMPLE_PREDICATE_NUMBER_OF_ARGUMENTS), narrationRuleEntity.getNumberOfArguments());
        
        assertEquals("Predicate natural language template should've been the one provided in the constructor",
                SAMPLE_PREDICATE_NATURAL_LANGUAGE_TEMPLATE, narrationRuleEntity.getNaturalLanguageTemplate());
    }
    
    @Test
    public void UT_NarrationRuleEntity_getters_must_return_set_values() throws Exception {
        PojoTester.testPojo(NarrationRuleEntity.class);
    }
}
