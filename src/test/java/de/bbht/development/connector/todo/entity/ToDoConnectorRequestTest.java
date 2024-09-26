package de.bbht.development.connector.todo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bbht.development.connector.service.dto.enums.*;
import io.camunda.connector.api.error.ConnectorInputException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ToDoConnectorRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReplaceSecretTokens() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, "test@bbht.de", null, null, null);
        var input = new ToDoConnectorRequest(authentication, operation, null, null, null, null, null, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var variables = context.bindVariables(ToDoConnectorRequest.class);

        // then
        Assertions.assertThat(variables)
                .extracting(ToDoConnectorRequest::authentication)
                .returns("secretTenantId", GraphAuthentication::tenantId)
                .returns("secretClientId", GraphAuthentication::clientId)
                .returns("secretClientSecret", GraphAuthentication::clientSecret);
    }

    @Test
    void shouldThrowExceptionOnNullOperation() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(null, "test@bbht.de", null, null, null);
        var input = new ToDoConnectorRequest(authentication, operation, null, null, null, null, null, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        assertThatThrownBy(() -> context.bindVariables(ToDoConnectorRequest.class))
                // then
                .isInstanceOf(ConnectorInputException.class)
                .hasMessageContaining("operation.operation: Validation failed.");
    }

    @Test
    void shouldThrowExceptionOnNullUserId() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, null, null, null, null);
        var input = new ToDoConnectorRequest(authentication, operation, null, null, null, null, null, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        assertThatThrownBy(() -> context.bindVariables(ToDoConnectorRequest.class))
                // then
                .isInstanceOf(ConnectorInputException.class)
                .hasMessageContaining("operation.userIdOrPrincipalName: Validation failed.");
    }

    @Test
    void shouldThrowExceptionOnEmptyUserId() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, "", null, null, null);
        var input = new ToDoConnectorRequest(authentication, operation, null, null, null, null, null, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        assertThatThrownBy(() -> context.bindVariables(ToDoConnectorRequest.class))
                // then
                .isInstanceOf(ConnectorInputException.class)
                .hasMessageContaining("operation.userIdOrPrincipalName: Validation failed.");
    }

    @Test
    void shouldWorkWithValidTaskListOptions() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, "test@bbht.de", null, null, null);
        var taskListOptions = new TaskListOptions("Display Name");
        var input = new ToDoConnectorRequest(authentication, operation, taskListOptions, null, null, null, null, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var variables = context.bindVariables(ToDoConnectorRequest.class);

        // then
        Assertions.assertThat(variables)
                .extracting(ToDoConnectorRequest::taskListOptions)
                .returns("Display Name", TaskListOptions::displayName);
    }

    @Test
    void shouldWorkWithValidTaskOptions() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, "test@bbht.de", null, null, null);
        var taskOptions = new TaskOptions("Titel", "Body", "Category 1, Category 2", ImportanceDto.HIGH,
                TaskStatusDto.IN_PROGRESS, createDateTime(2023), "UTC", createDateTime(2024), "UTC",
                createDateTime(2025), "UTC", createDateTime(2024), "UTC", Boolean.TRUE);
        var taskRecurrenceOptions = new TaskRecurrenceOptions(TaskRecurrenceOptions.VALUE_RECURRING, RecurrencePatternTypeDto.DAILY,
                5, 12, "Monday, Thursday", DayOfWeekDto.MONDAY, WeekIndexDto.FIRST, 7,
                RecurrenceRangeTypeDto.NUMBERED, 10, "2024-08-01",
                "2024-12-01", "UTC");
        var input = new ToDoConnectorRequest(authentication, operation, null, null, taskOptions, null, taskRecurrenceOptions, null, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var variables = context.bindVariables(ToDoConnectorRequest.class);

        // then
        Assertions.assertThat(variables)
                .extracting(ToDoConnectorRequest::taskOptions)
                .returns("Titel", TaskOptions::title)
                .returns("Body", TaskOptions::body)
                .returns("Category 1, Category 2", TaskOptions::categories)
                .returns(ImportanceDto.HIGH, TaskOptions::importance)
                .returns(TaskStatusDto.IN_PROGRESS, TaskOptions::status)
                .returns(createDateTime(2023), TaskOptions::startDateTime)
                .returns("UTC", TaskOptions::startDateTimeTimeZone)
                .returns(createDateTime(2024), TaskOptions::dueDateTime)
                .returns("UTC", TaskOptions::dueDateTimeTimeZone)
                .returns(createDateTime(2025), TaskOptions::completedDateTime)
                .returns("UTC", TaskOptions::completedDateTimeTimeZone)
                .returns(createDateTime(2024), TaskOptions::reminderDateTime)
                .returns("UTC", TaskOptions::reminderDateTimeTimeZone);
        Assertions.assertThat(variables)
                .extracting(ToDoConnectorRequest::taskRecurrenceOptions)
                .returns(TaskRecurrenceOptions.VALUE_RECURRING, TaskRecurrenceOptions::recurring)
                .returns(RecurrencePatternTypeDto.DAILY, TaskRecurrenceOptions::patternType)
                .returns(5, TaskRecurrenceOptions::interval)
                .returns(12, TaskRecurrenceOptions::dayOfMonth)
                .returns("Monday, Thursday", TaskRecurrenceOptions::daysOfWeek)
                .returns(DayOfWeekDto.MONDAY, TaskRecurrenceOptions::firstDayOfWeek)
                .returns(WeekIndexDto.FIRST, TaskRecurrenceOptions::index)
                .returns(7, TaskRecurrenceOptions::month)
                .returns(RecurrenceRangeTypeDto.NUMBERED, TaskRecurrenceOptions::rangeType)
                .returns(10, TaskRecurrenceOptions::numberOfOccurrences)
                .returns("2024-08-01", TaskRecurrenceOptions::startDate)
                .returns("2024-12-01", TaskRecurrenceOptions::endDate)
                .returns("UTC", TaskRecurrenceOptions::recurrenceTimeZone);
    }

    private String createDateTime(int year) {
        return (year + "-08-13T14:38:43.104312");
    }

    @Test
    void shouldWorkWithValidCheckListItemOptions() throws Exception {
        // Given
        var authentication = new GraphAuthentication("{{secrets.TENANT_ID}}", "{{secrets.CLIENT_ID}}",
                "{{secrets.CLIENT_SECRET}}");
        var operation = new Operation(ToDoOperation.LIST_TASK_LISTS, "test@bbht.de", null, null, null);
        var checkListItemOptions = new CheckListItemOptions("Display Name", Boolean.TRUE);
        var input = new ToDoConnectorRequest(authentication, operation, null, null, null,
                null, null, checkListItemOptions, null);

        var context = OutboundConnectorContextBuilder.create()
                .secret("TENANT_ID", "secretTenantId")
                .secret("CLIENT_ID", "secretClientId")
                .secret("CLIENT_SECRET", "secretClientSecret")
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var variables = context.bindVariables(ToDoConnectorRequest.class);

        // then
        Assertions.assertThat(variables)
                .extracting(ToDoConnectorRequest::checkListItemOptions)
                .returns("Display Name", CheckListItemOptions::displayName)
                .returns(Boolean.TRUE, CheckListItemOptions::checked);
    }
}
