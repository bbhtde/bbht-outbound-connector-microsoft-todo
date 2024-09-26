package de.bbht.development.connector.service.mapper;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.graph.models.RecurrenceRange;
import com.microsoft.graph.models.RecurrenceRangeType;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.task.RecurrenceRangeDto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class RecurrenceRangeMapperTest {

    private final LocalDate NOW = LocalDate.now();

    @Test
    void shouldMapRecurrenceRange() {
        // given
        var range = new RecurrenceRange();
        range.setType(RecurrenceRangeType.EndDate);
        range.setRecurrenceTimeZone("UTC");
        range.setEndDate(NOW.plusDays(5));
        range.setStartDate(NOW.minusDays(5));
        range.setNumberOfOccurrences(5);

        // when
        var result = RecurrenceRangeMapper.mapRecurrenceRange(range);

        // then
        assertThat(result)
                .isNotNull()
                .returns(RecurrenceRangeTypeDto.END_DATE, RecurrenceRangeDto::getType)
                .returns("UTC", RecurrenceRangeDto::getRecurrenceTimeZone)
                .returns(NOW.plusDays(5), RecurrenceRangeDto::getEndDate)
                .returns(NOW.minusDays(5), RecurrenceRangeDto::getStartDate)
                .returns(5, RecurrenceRangeDto::getNumberOfOccurrences);
    }

    @Test
    void shouldMapNullRecurrenceRange() {
        // given
        var range = (RecurrenceRange) null;

        // when
        var result = RecurrenceRangeMapper.mapRecurrenceRange(range);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapRecurrenceRangeDto() {
        // given
        var range = new RecurrenceRangeDto();
        range.setType(RecurrenceRangeTypeDto.NUMBERED);
        range.setRecurrenceTimeZone("UTC");
        range.setEndDate(NOW.plusDays(4));
        range.setStartDate(NOW.minusDays(4));
        range.setNumberOfOccurrences(3);

        // when
        var result = RecurrenceRangeMapper.mapRecurrenceRangeDto(range);

        // then
        assertThat(result)
                .isNotNull()
                .returns(RecurrenceRangeType.Numbered, RecurrenceRange::getType)
                .returns("UTC", RecurrenceRange::getRecurrenceTimeZone)
                .returns(NOW.plusDays(4), RecurrenceRange::getEndDate)
                .returns(NOW.minusDays(4), RecurrenceRange::getStartDate)
                .returns(3, RecurrenceRange::getNumberOfOccurrences);
    }

    @Test
    void shouldMapNullRecurrenceRangeDto() {
        // given
        var rangeDto = (RecurrenceRangeDto) null;

        // when
        var result = RecurrenceRangeMapper.mapRecurrenceRangeDto(rangeDto);

        // then
        assertThat(result).isNull();
    }
}
