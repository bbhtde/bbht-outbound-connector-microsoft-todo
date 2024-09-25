package de.bbht.development.connector.todo.entity;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import de.bbht.development.connector.service.dto.enums.DayOfWeekDto;
import de.bbht.development.connector.service.dto.enums.RecurrencePatternTypeDto;
import de.bbht.development.connector.service.dto.enums.RecurrenceRangeTypeDto;
import de.bbht.development.connector.service.dto.enums.WeekIndexDto;
import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.DropdownPropertyChoice;
import io.camunda.connector.generator.java.annotation.TemplateProperty.Pattern;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyCondition;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyConstraints;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;

public record TaskRecurrenceOptions(
    @TemplateProperty(
        group = "recurrence",
        label = "Recurring Task?",
        description = "Check if this is a recurring task",
        condition = @PropertyCondition(property = "operation.operation", oneOf = {"CREATE_TASK", "UPDATE_TASK"}),
        optional = false,
        defaultValue = "NO_RECURRING",
        type = PropertyType.Dropdown,
        constraints = @PropertyConstraints(notEmpty = true),
        choices = {
            @DropdownPropertyChoice(label = "Task is not recurring", value = "NO_RECURRING"),
            @DropdownPropertyChoice(label = "Recurring Task", value = "RECURRING"),
        })
    String recurring,

    @TemplateProperty(
      group = "recurrence",
      label = "Recurrence Pattern Type",
      description = "Select the recurrence pattern type",
      constraints = @PropertyConstraints(notEmpty = true),
      condition = @PropertyCondition(
          property = "taskRecurrenceOptions.recurring",
          equals = "RECURRING"
      ),
      type = PropertyType.Dropdown,
      choices = {
          @DropdownPropertyChoice(label = "Daily", value = "DAILY"),
          @DropdownPropertyChoice(label = "Weekly", value = "WEEKLY"),
          @DropdownPropertyChoice(label = "Monthly (absolute)", value = "ABSOLUTE_MONTHLY"),
          @DropdownPropertyChoice(label = "Monthly (relative)", value = "RELATIVE_MONTHLY"),
          @DropdownPropertyChoice(label = "Yearly (absolute)", value = "ABSOLUTE_YEARLY"),
          @DropdownPropertyChoice(label = "Yearly (relative)", value = "RELATIVE_YEARLY"),
      }
    )
    RecurrencePatternTypeDto patternType,

    @TemplateProperty(
        group = "recurrence",
        label = "Interval",
        description = "The number of units between occurrences (units are days, weeks, months or years depending on the pattern type)",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_32_BIT_INTEGER,
                message = "Please enter a positive integer (1 - 999999999)"
            )
        ),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.recurring",
            equals = "RECURRING"
        )
    )
    Integer interval,

    @TemplateProperty(
        group = "recurrence",
        label = "Day of Month",
        description = "The day of the month on which the Task occurs.",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_DAYS,
                message = "Please enter a day between 1 and 31"
            )
        ),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.patternType",
            oneOf = { "ABSOLUTE_MONTHLY", "ABSOLUTE_YEARLY" }
        )
    )
    Integer dayOfMonth,

    @TemplateProperty(
        group = "recurrence",
        label = "Days of Week",
        description = "A List of the Days of the Week on which the Task occurs.",
        constraints = @PropertyConstraints(
            pattern = @Pattern(
                value = ToDoConstants.REGEX_WEEKDAYS,
                message = "Please enter a comma-separated list of weekday names (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday). Each name may appear only once."
            )
        ),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.patternType",
            oneOf = { "WEEKLY", "RELATIVE_MONTHLY", "RELATIVE_YEARLY" }
        )
    )
    String daysOfWeek,

    @TemplateProperty(
        group = "recurrence",
        label = "First Day of Week",
        description = "The first day of the week.",
        constraints = @PropertyConstraints(notEmpty = true),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.patternType",
            equals = "WEEKLY"
        ),
        type = PropertyType.Dropdown,
        choices = {
            @DropdownPropertyChoice(label = "Sunday", value = "SUNDAY"),
            @DropdownPropertyChoice(label = "Monday", value = "MONDAY"),
            @DropdownPropertyChoice(label = "Tuesday", value = "TUESDAY"),
            @DropdownPropertyChoice(label = "Wednesday", value = "WEDNESDAY"),
            @DropdownPropertyChoice(label = "Thursday", value = "THURSDAY"),
            @DropdownPropertyChoice(label = "Friday", value = "FRIDAY"),
            @DropdownPropertyChoice(label = "Saturday", value = "SATURDAY"),
        }
    )
    DayOfWeekDto firstDayOfWeek,

    @TemplateProperty(
        group = "recurrence",
        label = "Week Index",
        description = "Specifies on which instance of the allowed days specified in Days of Week the task occurs, counted from the first instance of the month.",
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.patternType",
            oneOf = { "RELATIVE_MONTHLY", "RELATIVE_YEARLY" }
        ),
        optional = true,
        type = PropertyType.Dropdown,
        choices = {
            @DropdownPropertyChoice(label = "First", value = "FIRST"),
            @DropdownPropertyChoice(label = "Second", value = "SECOND"),
            @DropdownPropertyChoice(label = "Third", value = "THIRD"),
            @DropdownPropertyChoice(label = "Fourth", value = "FOURTH"),
            @DropdownPropertyChoice(label = "Last", value = "LAST"),
        }
    )
    WeekIndexDto index,

    @TemplateProperty(
        group = "recurrence",
        label = "Month",
        description = "The month in which the task occurs.",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_MONTHS,
                message = "Please enter a number from 1 to 12"
            )
        ),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.patternType",
            oneOf = { "ABSOLUTE_YEARLY", "RELATIVE_YEARLY" }
        ))
    Integer month,

    @TemplateProperty(
        group= "recurrence",
        label = "Recurrance Range Type",
        description = "Select the recurrence range type",
        constraints = @PropertyConstraints(notEmpty = true),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.recurring",
            equals = "RECURRING"
        ),
        choices = {
            @DropdownPropertyChoice(label = "End Date", value = "END_DATE"),
            @DropdownPropertyChoice(label = "No End", value = "NO_END"),
            @DropdownPropertyChoice(label = "Numbered ", value = "NUMBERED")
        })
    RecurrenceRangeTypeDto rangeType,

    @TemplateProperty(
        group = "recurrence",
        label = "Number of Occurrences",
        description = "The number of times to repeat the task",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_32_BIT_INTEGER,
                message = "Please enter a positive integer (1 - 999999999)"
            )
        ),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.rangeType",
            equals = "NUMBERED"
        )
    )
    Integer numberOfOccurrences,

    @TemplateProperty(
        group = "recurrence",
        label = "Start Date",
        description = "The date to start applying the recurrence pattern.",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_DATETIME,
                message = "Please use the format 'YYYY-MM-DDThh:mm:ss.SSSSSS'")),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.recurring",
            equals = "RECURRING"
        )
    )
    String startDate,

    @TemplateProperty(
        group = "recurrence",
        label = "End Date",
        description = "The date to start applying the recurrence pattern.",
        constraints = @PropertyConstraints(
            notEmpty = true,
            pattern = @Pattern(
                value = ToDoConstants.REGEX_DATETIME,
                message = "Please use the format 'YYYY-MM-DDThh:mm:ss.SSSSSS'")),
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.rangeType",
            equals = "END_DATE"
        )
    )
    String endDate,

    @TemplateProperty(
        group = "recurrence", label = "Start & End Date Timezone",
        description = "Timezone for the Start and End Date",
        condition = @PropertyCondition(
            property = "taskRecurrenceOptions.recurring",
            equals = "RECURRING"),
        choices = {
            @DropdownPropertyChoice(value = "Afghanistan Standard Time", label = "Afghanistan Standard Time"),
            @DropdownPropertyChoice(value = "Africa/Cairo", label = "Africa/Cairo"),
            @DropdownPropertyChoice(value = "Africa/Casablanca", label = "Africa/Casablanca"),
            @DropdownPropertyChoice(value = "Africa/Johannesburg", label = "Africa/Johannesburg"),
            @DropdownPropertyChoice(value = "Africa/Lagos", label = "Africa/Lagos"),
            @DropdownPropertyChoice(value = "Africa/Nairobi", label = "Africa/Nairobi"),
            @DropdownPropertyChoice(value = "Africa/Windhoek", label = "Africa/Windhoek"),
            @DropdownPropertyChoice(value = "Alaskan Standard Time", label = "Alaskan Standard Time"),
            @DropdownPropertyChoice(value = "Aleutian Standard Time", label = "Aleutian Standard Time"),
            @DropdownPropertyChoice(value = "Altai Standard Time", label = "Altai Standard Time"),
            @DropdownPropertyChoice(value = "America/Anchorage", label = "America/Anchorage"),
            @DropdownPropertyChoice(value = "America/Argentina/Buenos_Aires", label = "America/Argentina/Buenos_Aires"),
            @DropdownPropertyChoice(value = "America/Asuncion", label = "America/Asuncion"),
            @DropdownPropertyChoice(value = "America/Bahia", label = "America/Bahia"),
            @DropdownPropertyChoice(value = "America/Bogota", label = "America/Bogota"),
            @DropdownPropertyChoice(value = "America/Caracas", label = "America/Caracas"),
            @DropdownPropertyChoice(value = "America/Cayenne", label = "America/Cayenne"),
            @DropdownPropertyChoice(value = "America/Chicago", label = "America/Chicago"),
            @DropdownPropertyChoice(value = "America/Chihuahua", label = "America/Chihuahua"),
            @DropdownPropertyChoice(value = "America/Cuiaba", label = "America/Cuiaba"),
            @DropdownPropertyChoice(value = "America/Denver", label = "America/Denver"),
            @DropdownPropertyChoice(value = "America/Godthab", label = "America/Godthab"),
            @DropdownPropertyChoice(value = "America/Guatemala", label = "America/Guatemala"),
            @DropdownPropertyChoice(value = "America/Halifax", label = "America/Halifax"),
            @DropdownPropertyChoice(value = "America/Indiana/Indianapolis", label = "America/Indiana/Indianapolis"),
            @DropdownPropertyChoice(value = "America/La_Paz", label = "America/La_Paz"),
            @DropdownPropertyChoice(value = "America/Los_Angeles", label = "America/Los_Angeles"),
            @DropdownPropertyChoice(value = "America/Mexico_City", label = "America/Mexico_City"),
            @DropdownPropertyChoice(value = "America/Montevideo", label = "America/Montevideo"),
            @DropdownPropertyChoice(value = "America/New_York", label = "America/New_York"),
            @DropdownPropertyChoice(value = "America/Phoenix", label = "America/Phoenix"),
            @DropdownPropertyChoice(value = "America/Regina", label = "America/Regina"),
            @DropdownPropertyChoice(value = "America/Santa_Isabel", label = "America/Santa_Isabel"),
            @DropdownPropertyChoice(value = "America/Santiago", label = "America/Santiago"),
            @DropdownPropertyChoice(value = "America/Sao_Paulo", label = "America/Sao_Paulo"),
            @DropdownPropertyChoice(value = "America/St_Johns", label = "America/St_Johns"),
            @DropdownPropertyChoice(value = "Arab Standard Time", label = "Arab Standard Time"),
            @DropdownPropertyChoice(value = "Arabian Standard Time", label = "Arabian Standard Time"),
            @DropdownPropertyChoice(value = "Arabic Standard Time", label = "Arabic Standard Time"),
            @DropdownPropertyChoice(value = "Argentina Standard Time",
                label = "Argentina Standard Time"),
            @DropdownPropertyChoice(value = "Asia/Amman", label = "Asia/Amman"),
            @DropdownPropertyChoice(value = "Asia/Astana (Almaty)", label = "Asia/Astana (Almaty)"),
            @DropdownPropertyChoice(value = "Asia/Baghdad", label = "Asia/Baghdad"),
            @DropdownPropertyChoice(value = "Asia/Baku", label = "Asia/Baku"),
            @DropdownPropertyChoice(value = "Asia/Bangkok", label = "Asia/Bangkok"),
            @DropdownPropertyChoice(value = "Asia/Beirut", label = "Asia/Beirut"),
            @DropdownPropertyChoice(value = "Asia/Colombo", label = "Asia/Colombo"),
            @DropdownPropertyChoice(value = "Asia/Damascus", label = "Asia/Damascus"),
            @DropdownPropertyChoice(value = "Asia/Dhaka", label = "Asia/Dhaka"),
            @DropdownPropertyChoice(value = "Asia/Dubai", label = "Asia/Dubai"),
            @DropdownPropertyChoice(value = "Asia/Irkutsk", label = "Asia/Irkutsk"),
            @DropdownPropertyChoice(value = "Asia/Jerusalem", label = "Asia/Jerusalem"),
            @DropdownPropertyChoice(value = "Asia/Kabul", label = "Asia/Kabul"),
            @DropdownPropertyChoice(value = "Asia/Karachi", label = "Asia/Karachi"),
            @DropdownPropertyChoice(value = "Asia/Kathmandu", label = "Asia/Kathmandu"),
            @DropdownPropertyChoice(value = "Asia/Kolkata", label = "Asia/Kolkata"),
            @DropdownPropertyChoice(value = "Asia/Krasnoyarsk", label = "Asia/Krasnoyarsk"),
            @DropdownPropertyChoice(value = "Asia/Magadan", label = "Asia/Magadan"),
            @DropdownPropertyChoice(value = "Asia/Novosibirsk", label = "Asia/Novosibirsk"),
            @DropdownPropertyChoice(value = "Asia/Riyadh", label = "Asia/Riyadh"),
            @DropdownPropertyChoice(value = "Asia/Seoul", label = "Asia/Seoul"),
            @DropdownPropertyChoice(value = "Asia/Shanghai", label = "Asia/Shanghai"),
            @DropdownPropertyChoice(value = "Asia/Singapore", label = "Asia/Singapore"),
            @DropdownPropertyChoice(value = "Asia/Taipei", label = "Asia/Taipei"),
            @DropdownPropertyChoice(value = "Asia/Tbilisi", label = "Asia/Tbilisi"),
            @DropdownPropertyChoice(value = "Asia/Tehran", label = "Asia/Tehran"),
            @DropdownPropertyChoice(value = "Asia/Tokyo", label = "Asia/Tokyo"),
            @DropdownPropertyChoice(value = "Asia/Toshkent (Tashkent)",
                label = "Asia/Toshkent (Tashkent)"),
            @DropdownPropertyChoice(value = "Asia/Ulaanbaatar", label = "Asia/Ulaanbaatar"),
            @DropdownPropertyChoice(value = "Asia/Vladivostok", label = "Asia/Vladivostok"),
            @DropdownPropertyChoice(value = "Asia/Yakutsk", label = "Asia/Yakutsk"),
            @DropdownPropertyChoice(value = "Asia/Yangon (Rangoon)", label = "Asia/Yangon (Rangoon)"),
            @DropdownPropertyChoice(value = "Asia/Yekaterinburg", label = "Asia/Yekaterinburg"),
            @DropdownPropertyChoice(value = "Asia/Yerevan", label = "Asia/Yerevan"),
            @DropdownPropertyChoice(value = "Astrakhan Standard Time",
                label = "Astrakhan Standard Time"),
            @DropdownPropertyChoice(value = "Atlantic Standard Time",
                label = "Atlantic Standard Time"),
            @DropdownPropertyChoice(value = "Atlantic/Azores", label = "Atlantic/Azores"),
            @DropdownPropertyChoice(value = "Atlantic/Cape_Verde", label = "Atlantic/Cape_Verde"),
            @DropdownPropertyChoice(value = "Atlantic/Reykjavik", label = "Atlantic/Reykjavik"),
            @DropdownPropertyChoice(value = "AUS Central Standard Time",
                label = "AUS Central Standard Time"),
            @DropdownPropertyChoice(value = "Aus Central W. Standard Time",
                label = "Aus Central W. Standard Time"),
            @DropdownPropertyChoice(value = "AUS Eastern Standard Time",
                label = "AUS Eastern Standard Time"),
            @DropdownPropertyChoice(value = "Australia/Adelaide", label = "Australia/Adelaide"),
            @DropdownPropertyChoice(value = "Australia/Brisbane", label = "Australia/Brisbane"),
            @DropdownPropertyChoice(value = "Australia/Darwin", label = "Australia/Darwin"),
            @DropdownPropertyChoice(value = "Australia/Hobart", label = "Australia/Hobart"),
            @DropdownPropertyChoice(value = "Australia/Perth", label = "Australia/Perth"),
            @DropdownPropertyChoice(value = "Australia/Sydney", label = "Australia/Sydney"),
            @DropdownPropertyChoice(value = "Azerbaijan Standard Time",
                label = "Azerbaijan Standard Time"),
            @DropdownPropertyChoice(value = "Azores Standard Time", label = "Azores Standard Time"),
            @DropdownPropertyChoice(value = "Bahia Standard Time", label = "Bahia Standard Time"),
            @DropdownPropertyChoice(value = "Bangladesh Standard Time",
                label = "Bangladesh Standard Time"),
            @DropdownPropertyChoice(value = "Belarus Standard Time", label = "Belarus Standard Time"),
            @DropdownPropertyChoice(value = "Bougainville Standard Time",
                label = "Bougainville Standard Time"),
            @DropdownPropertyChoice(value = "Canada Central Standard Time",
                label = "Canada Central Standard Time"),
            @DropdownPropertyChoice(value = "Cape Verde Standard Time",
                label = "Cape Verde Standard Time"),
            @DropdownPropertyChoice(value = "Caucasus Standard Time",
                label = "Caucasus Standard Time"),
            @DropdownPropertyChoice(value = "Cen. Australia Standard Time",
                label = "Cen. Australia Standard Time"),
            @DropdownPropertyChoice(value = "Central America Standard Time",
                label = "Central America Standard Time"),
            @DropdownPropertyChoice(value = "Central Asia Standard Time",
                label = "Central Asia Standard Time"),
            @DropdownPropertyChoice(value = "Central Brazilian Standard Time",
                label = "Central Brazilian Standard Time"),
            @DropdownPropertyChoice(value = "Central Europe Standard Time",
                label = "Central Europe Standard Time"),
            @DropdownPropertyChoice(value = "Central European Standard Time",
                label = "Central European Standard Time"),
            @DropdownPropertyChoice(value = "Central Pacific Standard Time",
                label = "Central Pacific Standard Time"),
            @DropdownPropertyChoice(value = "Central Standard Time", label = "Central Standard Time"),
            @DropdownPropertyChoice(value = "Central Standard Time (Mexico)",
                label = "Central Standard Time (Mexico)"),
            @DropdownPropertyChoice(value = "Chatham Islands Standard Time",
                label = "Chatham Islands Standard Time"),
            @DropdownPropertyChoice(value = "China Standard Time", label = "China Standard Time"),
            @DropdownPropertyChoice(value = "Cuba Standard Time", label = "Cuba Standard Time"),
            @DropdownPropertyChoice(value = "Dateline Standard Time",
                label = "Dateline Standard Time"),
            @DropdownPropertyChoice(value = "E. Africa Standard Time",
                label = "E. Africa Standard Time"),
            @DropdownPropertyChoice(value = "E. Australia Standard Time",
                label = "E. Australia Standard Time"),
            @DropdownPropertyChoice(value = "E. Europe Standard Time",
                label = "E. Europe Standard Time"),
            @DropdownPropertyChoice(value = "E. South America Standard Time",
                label = "E. South America Standard Time"),
            @DropdownPropertyChoice(value = "Easter Island Standard Time",
                label = "Easter Island Standard Time"),
            @DropdownPropertyChoice(value = "Eastern Standard Time", label = "Eastern Standard Time"),
            @DropdownPropertyChoice(value = "Eastern Standard Time (Mexico)",
                label = "Eastern Standard Time (Mexico)"),
            @DropdownPropertyChoice(value = "Egypt Standard Time", label = "Egypt Standard Time"),
            @DropdownPropertyChoice(value = "Ekaterinburg Standard Time",
                label = "Ekaterinburg Standard Time"),
            @DropdownPropertyChoice(value = "Etc/GMT", label = "Etc/GMT"),
            @DropdownPropertyChoice(value = "Etc/GMT-12", label = "Etc/GMT-12"),
            @DropdownPropertyChoice(value = "Etc/GMT+11", label = "Etc/GMT+11"),
            @DropdownPropertyChoice(value = "Etc/GMT+12", label = "Etc/GMT+12"),
            @DropdownPropertyChoice(value = "Etc/GMT+2", label = "Etc/GMT+2"),
            @DropdownPropertyChoice(value = "Europe/Berlin", label = "Europe/Berlin"),
            @DropdownPropertyChoice(value = "Europe/Bucharest", label = "Europe/Bucharest"),
            @DropdownPropertyChoice(value = "Europe/Budapest", label = "Europe/Budapest"),
            @DropdownPropertyChoice(value = "Europe/Istanbul", label = "Europe/Istanbul"),
            @DropdownPropertyChoice(value = "Europe/Kaliningrad", label = "Europe/Kaliningrad"),
            @DropdownPropertyChoice(value = "Europe/Kyiv (Kiev)", label = "Europe/Kyiv (Kiev)"),
            @DropdownPropertyChoice(value = "Europe/London", label = "Europe/London"),
            @DropdownPropertyChoice(value = "Europe/Moscow", label = "Europe/Moscow"),
            @DropdownPropertyChoice(value = "Europe/Paris", label = "Europe/Paris"),
            @DropdownPropertyChoice(value = "Europe/Warsaw", label = "Europe/Warsaw"),
            @DropdownPropertyChoice(value = "Fiji Standard Time", label = "Fiji Standard Time"),
            @DropdownPropertyChoice(value = "FLE Standard Time", label = "FLE Standard Time"),
            @DropdownPropertyChoice(value = "Georgian Standard Time",
                label = "Georgian Standard Time"),
            @DropdownPropertyChoice(value = "GMT Standard Time", label = "GMT Standard Time"),
            @DropdownPropertyChoice(value = "Greenland Standard Time",
                label = "Greenland Standard Time"),
            @DropdownPropertyChoice(value = "Greenwich Standard Time",
                label = "Greenwich Standard Time"),
            @DropdownPropertyChoice(value = "GTB Standard Time", label = "GTB Standard Time"),
            @DropdownPropertyChoice(value = "Haiti Standard Time", label = "Haiti Standard Time"),
            @DropdownPropertyChoice(value = "Hawaiian Standard Time",
                label = "Hawaiian Standard Time"),
            @DropdownPropertyChoice(value = "India Standard Time", label = "India Standard Time"),
            @DropdownPropertyChoice(value = "Indian/Mauritius", label = "Indian/Mauritius"),
            @DropdownPropertyChoice(value = "Iran Standard Time", label = "Iran Standard Time"),
            @DropdownPropertyChoice(value = "Israel Standard Time", label = "Israel Standard Time"),
            @DropdownPropertyChoice(value = "Jordan Standard Time", label = "Jordan Standard Time"),
            @DropdownPropertyChoice(value = "Kaliningrad Standard Time",
                label = "Kaliningrad Standard Time"),
            @DropdownPropertyChoice(value = "Korea Standard Time", label = "Korea Standard Time"),
            @DropdownPropertyChoice(value = "Libya Standard Time", label = "Libya Standard Time"),
            @DropdownPropertyChoice(value = "Line Islands Standard Time",
                label = "Line Islands Standard Time"),
            @DropdownPropertyChoice(value = "Lord Howe Standard Time",
                label = "Lord Howe Standard Time"),
            @DropdownPropertyChoice(value = "Magadan Standard Time", label = "Magadan Standard Time"),
            @DropdownPropertyChoice(value = "Magallanes Standard Time",
                label = "Magallanes Standard Time"),
            @DropdownPropertyChoice(value = "Marquesas Standard Time",
                label = "Marquesas Standard Time"),
            @DropdownPropertyChoice(value = "Mauritius Standard Time",
                label = "Mauritius Standard Time"),
            @DropdownPropertyChoice(value = "Middle East Standard Time",
                label = "Middle East Standard Time"),
            @DropdownPropertyChoice(value = "Montevideo Standard Time",
                label = "Montevideo Standard Time"),
            @DropdownPropertyChoice(value = "Morocco Standard Time", label = "Morocco Standard Time"),
            @DropdownPropertyChoice(value = "Mountain Standard Time",
                label = "Mountain Standard Time"),
            @DropdownPropertyChoice(value = "Mountain Standard Time (Mexico)",
                label = "Mountain Standard Time (Mexico)"),
            @DropdownPropertyChoice(value = "Myanmar Standard Time", label = "Myanmar Standard Time"),
            @DropdownPropertyChoice(value = "N. Central Asia Standard Time",
                label = "N. Central Asia Standard Time"),
            @DropdownPropertyChoice(value = "Namibia Standard Time", label = "Namibia Standard Time"),
            @DropdownPropertyChoice(value = "Nepal Standard Time", label = "Nepal Standard Time"),
            @DropdownPropertyChoice(value = "New Zealand Standard Time",
                label = "New Zealand Standard Time"),
            @DropdownPropertyChoice(value = "Newfoundland Standard Time",
                label = "Newfoundland Standard Time"),
            @DropdownPropertyChoice(value = "Norfolk Standard Time", label = "Norfolk Standard Time"),
            @DropdownPropertyChoice(value = "North Asia East Standard Time",
                label = "North Asia East Standard Time"),
            @DropdownPropertyChoice(value = "North Asia Standard Time",
                label = "North Asia Standard Time"),
            @DropdownPropertyChoice(value = "North Korea Standard Time",
                label = "North Korea Standard Time"),
            @DropdownPropertyChoice(value = "Omsk Standard Time", label = "Omsk Standard Time"),
            @DropdownPropertyChoice(value = "Pacific SA Standard Time",
                label = "Pacific SA Standard Time"),
            @DropdownPropertyChoice(value = "Pacific Standard Time", label = "Pacific Standard Time"),
            @DropdownPropertyChoice(value = "Pacific Standard Time (Mexico)",
                label = "Pacific Standard Time (Mexico)"),
            @DropdownPropertyChoice(value = "Pacific/Apia", label = "Pacific/Apia"),
            @DropdownPropertyChoice(value = "Pacific/Auckland", label = "Pacific/Auckland"),
            @DropdownPropertyChoice(value = "Pacific/Fiji", label = "Pacific/Fiji"),
            @DropdownPropertyChoice(value = "Pacific/Guadalcanal", label = "Pacific/Guadalcanal"),
            @DropdownPropertyChoice(value = "Pacific/Honolulu", label = "Pacific/Honolulu"),
            @DropdownPropertyChoice(value = "Pacific/Kiritimati", label = "Pacific/Kiritimati"),
            @DropdownPropertyChoice(value = "Pacific/Port_Moresby", label = "Pacific/Port_Moresby"),
            @DropdownPropertyChoice(value = "Pacific/Tongatapu", label = "Pacific/Tongatapu"),
            @DropdownPropertyChoice(value = "Pakistan Standard Time",
                label = "Pakistan Standard Time"),
            @DropdownPropertyChoice(value = "Paraguay Standard Time",
                label = "Paraguay Standard Time"),
            @DropdownPropertyChoice(value = "Qyzylorda Standard Time",
                label = "Qyzylorda Standard Time"),
            @DropdownPropertyChoice(value = "Romance Standard Time", label = "Romance Standard Time"),
            @DropdownPropertyChoice(value = "Russia Time Zone 10", label = "Russia Time Zone 10"),
            @DropdownPropertyChoice(value = "Russia Time Zone 11", label = "Russia Time Zone 11"),
            @DropdownPropertyChoice(value = "Russia Time Zone 3", label = "Russia Time Zone 3"),
            @DropdownPropertyChoice(value = "Russian Standard Time", label = "Russian Standard Time"),
            @DropdownPropertyChoice(value = "SA Eastern Standard Time",
                label = "SA Eastern Standard Time"),
            @DropdownPropertyChoice(value = "SA Pacific Standard Time",
                label = "SA Pacific Standard Time"),
            @DropdownPropertyChoice(value = "SA Western Standard Time",
                label = "SA Western Standard Time"),
            @DropdownPropertyChoice(value = "Saint Pierre Standard Time",
                label = "Saint Pierre Standard Time"),
            @DropdownPropertyChoice(value = "Sakhalin Standard Time",
                label = "Sakhalin Standard Time"),
            @DropdownPropertyChoice(value = "Samoa Standard Time", label = "Samoa Standard Time"),
            @DropdownPropertyChoice(value = "Sao Tome Standard Time",
                label = "Sao Tome Standard Time"),
            @DropdownPropertyChoice(value = "Saratov Standard Time", label = "Saratov Standard Time"),
            @DropdownPropertyChoice(value = "SE Asia Standard Time", label = "SE Asia Standard Time"),
            @DropdownPropertyChoice(value = "Singapore Standard Time",
                label = "Singapore Standard Time"),
            @DropdownPropertyChoice(value = "South Africa Standard Time",
                label = "South Africa Standard Time"),
            @DropdownPropertyChoice(value = "South Sudan Standard Time",
                label = "South Sudan Standard Time"),
            @DropdownPropertyChoice(value = "Sri Lanka Standard Time",
                label = "Sri Lanka Standard Time"),
            @DropdownPropertyChoice(value = "Sudan Standard Time", label = "Sudan Standard Time"),
            @DropdownPropertyChoice(value = "Syria Standard Time", label = "Syria Standard Time"),
            @DropdownPropertyChoice(value = "Taipei Standard Time", label = "Taipei Standard Time"),
            @DropdownPropertyChoice(value = "Tasmania Standard Time",
                label = "Tasmania Standard Time"),
            @DropdownPropertyChoice(value = "Tocantins Standard Time",
                label = "Tocantins Standard Time"),
            @DropdownPropertyChoice(value = "Tokyo Standard Time", label = "Tokyo Standard Time"),
            @DropdownPropertyChoice(value = "Tomsk Standard Time", label = "Tomsk Standard Time"),
            @DropdownPropertyChoice(value = "Tonga Standard Time", label = "Tonga Standard Time"),
            @DropdownPropertyChoice(value = "Transbaikal Standard Time",
                label = "Transbaikal Standard Time"),
            @DropdownPropertyChoice(value = "Turkey Standard Time", label = "Turkey Standard Time"),
            @DropdownPropertyChoice(value = "Turks And Caicos Standard Time",
                label = "Turks And Caicos Standard Time"),
            @DropdownPropertyChoice(value = "Ulaanbaatar Standard Time",
                label = "Ulaanbaatar Standard Time"),
            @DropdownPropertyChoice(value = "US Eastern Standard Time",
                label = "US Eastern Standard Time"),
            @DropdownPropertyChoice(value = "US Mountain Standard Time",
                label = "US Mountain Standard Time"),
            @DropdownPropertyChoice(value = "UTC", label = "UTC"),
            @DropdownPropertyChoice(value = "UTC-02", label = "UTC-02"),
            @DropdownPropertyChoice(value = "UTC-08", label = "UTC-08"),
            @DropdownPropertyChoice(value = "UTC-09", label = "UTC-09"),
            @DropdownPropertyChoice(value = "UTC-11", label = "UTC-11"),
            @DropdownPropertyChoice(value = "UTC+12", label = "UTC+12"),
            @DropdownPropertyChoice(value = "UTC+13", label = "UTC+13"),
            @DropdownPropertyChoice(value = "Venezuela Standard Time",
                label = "Venezuela Standard Time"),
            @DropdownPropertyChoice(value = "Vladivostok Standard Time",
                label = "Vladivostok Standard Time"),
            @DropdownPropertyChoice(value = "Volgograd Standard Time",
                label = "Volgograd Standard Time"),
            @DropdownPropertyChoice(value = "W. Australia Standard Time",
                label = "W. Australia Standard Time"),
            @DropdownPropertyChoice(value = "W. Central Africa Standard Time",
                label = "W. Central Africa Standard Time"),
            @DropdownPropertyChoice(value = "W. Europe Standard Time",
                label = "W. Europe Standard Time"),
            @DropdownPropertyChoice(value = "W. Mongolia Standard Time",
                label = "W. Mongolia Standard Time"),
            @DropdownPropertyChoice(value = "West Asia Standard Time",
                label = "West Asia Standard Time"),
            @DropdownPropertyChoice(value = "West Bank Standard Time",
                label = "West Bank Standard Time"),
            @DropdownPropertyChoice(value = "West Pacific Standard Time",
                label = "West Pacific Standard Time"),
            @DropdownPropertyChoice(value = "Yakutsk Standard Time", label = "Yakutsk Standard Time"),
            @DropdownPropertyChoice(value = "Yukon Standard Time", label = "Yukon Standard Time"),},
        type = TemplateProperty.PropertyType.Dropdown, 
        optional = true) 
    String recurrenceTimeZone
) {

}