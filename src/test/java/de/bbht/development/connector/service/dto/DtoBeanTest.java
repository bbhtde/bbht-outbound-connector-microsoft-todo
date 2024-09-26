package de.bbht.development.connector.service.dto;

import com.google.code.beanmatchers.BeanMatchers;
import de.bbht.development.connector.service.dto.checklistitem.CheckListItemDto;
import de.bbht.development.connector.service.dto.checklistitem.CreateUpdateCheckListItemDto;
import de.bbht.development.connector.service.dto.task.*;
import de.bbht.development.connector.service.dto.tasklist.CreateUpdateTaskListDto;
import de.bbht.development.connector.service.dto.tasklist.TaskListDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Random;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class DtoBeanTest {

  @BeforeAll
  static void setUp() {
    final Random random = new Random();
    BeanMatchers.registerValueGenerator(() -> OffsetDateTime.now()
        .plusDays(random.nextInt(1, 125)), OffsetDateTime.class);
    BeanMatchers.registerValueGenerator(() -> LocalDate.now()
        .plusDays(random.nextInt(0, 365)), LocalDate.class);
  }

  // We can use HamCrest and Bean Matchers in a parameterized test to ensure that our beans adhere to the standard
  // contract.
  @ParameterizedTest
  @ValueSource(classes = {TaskListDto.class, CreateUpdateTaskListDto.class, TaskDto.class, CreateUpdateTaskDto.class,
      CheckListItemDto.class, CreateUpdateCheckListItemDto.class, DateTimeTimeZoneDto.class,
      PatternedRecurrenceDto.class, RecurrencePatternDto.class, RecurrenceRangeDto.class})
  void shouldEnsureThatDtosAreValidBeans(Class<?> classUnderTest) throws Exception {
    var objectUnderTest = classUnderTest.getConstructor()
        .newInstance();

    assertThat(objectUnderTest, notNullValue());

    assertThat(classUnderTest,
        allOf(hasValidBeanConstructor(), hasValidGettersAndSetters(), hasValidBeanEquals(), hasValidBeanHashCode(),
            hasValidBeanToStringExcluding()));
  }

}
