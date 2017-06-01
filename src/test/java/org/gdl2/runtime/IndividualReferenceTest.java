package org.gdl2.runtime;

import org.gdl2.cdshooks.Card;
import org.gdl2.model.Guideline;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IndividualReferenceTest extends TestCommon {
    private Interpreter interpreter;
    private List<DataInstance> input;
    private List<Guideline> guidelines;

    @BeforeMethod
    public void setUp() throws Exception {
        input = new ArrayList<>();
        interpreter = new Interpreter();
    }

    @Test
    public void can_set_card_source_with_specific_reference_expect_found() throws Exception {
        guidelines = loadSingleGuideline("use_template_with_card_and_reference_test.v0.1.gdl2");
        List<DataInstance> output = interpreter.executeGuidelines(guidelines, input);
        assertThat(output.get(0).getRoot(), instanceOf(Card.class));
        Card card = (Card) output.get(0).getRoot();
        assertThat(card.getSource().getLabel(), is("NICE guideline"));
        assertThat(card.getSource().getUrl().toString(), is("https://www.nice.org.uk/guidance/CG181"));
    }

    @Test
    public void can_set_card_source_with_specific_reference_expect_not_found() throws Exception {
        guidelines = loadSingleGuideline("use_template_with_card_and_reference_not_found_test.v0.1.gdl2");
        List<DataInstance> output = interpreter.executeGuidelines(guidelines, input);
        assertThat(output.get(0).getRoot(), instanceOf(Card.class));
        Card card = (Card) output.get(0).getRoot();
        assertThat(card.getSource().getLabel(), is("Reference not found"));
    }
}
