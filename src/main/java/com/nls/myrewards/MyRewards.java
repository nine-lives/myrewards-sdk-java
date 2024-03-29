package com.nls.myrewards;

import com.nls.myrewards.client.HttpClient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MyRewards SDK entry point
 */
public final class MyRewards {
    private final HttpClient client;

    private MyRewards(MyRewardsConfiguration configuration) {
        this.client = new HttpClient(configuration);
    }

    /**
     * Get a MyRewards instance for your given api key.
     *
     * @param apiKey your client id
     * @return a MyRewards instance
     */
    public static MyRewards make(String apiKey) {
        return new MyRewards(new MyRewardsConfiguration()
                .withApiKey(apiKey));
    }

    /**
     * Get a MyRewards instance using finer grained control over configuration.
     *
     * @param configuration your configuration
     * @return a MyRewards instance
     */
    public static MyRewards make(MyRewardsConfiguration configuration) {
        return new MyRewards(configuration);
    }

    /**
     * Permissions are used to grant access to administrative and reporting areas of the programme. They are also
     * used to manage user access and ordering rights
     *
     * @return all permissions for the current stack
     */
    public List<MyRewardsPermission> getPermissions() {
        return client.get("/api/v2/permissions", null, MyRewardsPermission.LIST_TYPE_REFERENCE);
    }

    /**
     * A simple endpoint will be created to fetch registration_questions for this programme (Again scoped by API key).
     * This is potentially optional as a list of registration_question_ids could be provided, although this list will
     * have to be managed and maintained carefully. Should any new mandatory fields be added to this list, it would
     * require development on the client side of the API unless this list can be dynamically consulted via the api.
     *
     * @return a list of registration questions
     */
    public List<MyRewardsRegistrationQuestion> getRegistrationQuestions() {
        return client.get("/api/v2/registration_questions", null, MyRewardsRegistrationQuestion.LIST_TYPE_REFERENCE);
    }

    /**
     * An endpoint to fetch the list of values associated with a registration question. Returns an array of values
     * with their name and id.
     *
     * @param questionId the question id
     * @return a list of values for the question
     */
    public List<MyRewardsRegistrationQuestionValue> getRegistrationQuestionValues(int questionId) {
        return client.get(
                String.format("/api/v2/registration_questions/%d/list_of_values", questionId),
                null,
                MyRewardsRegistrationQuestionValue.LIST_TYPE_REFERENCE);
    }

    /**
     * Fetch a value by id. This really just calls the list of all values and filters it out, so it's not efficient
     *
     * @param questionId the question id
     * @param valueId the value id
     * @return the value or null
     */
    public MyRewardsRegistrationQuestionValue getRegistrationQuestionValue(int questionId, int valueId) {
        return getRegistrationQuestionValues(questionId).stream().filter(o -> o.getId() == valueId).findAny().orElse(null);
    }

    /**
     * Fetch a value by the name. This really just calls the list of all values and filters it out, so it's not efficient
     *
     * @param questionId the question id
     * @param name the name
     * @return the value or null
     */
    public MyRewardsRegistrationQuestionValue getRegistrationQuestionValue(int questionId, String name) {
        return getRegistrationQuestionValues(questionId).stream().filter(o -> o.getName().equals(name)).findAny().orElse(null);
    }

    /**
     * An endpoint to create a new list value for a registration question. On completion, returns an array of all
     * values with their name and id.
     *
     * @param questionId the question id
     * @param name       the name of the new list value
     * @return the created value
     */
    public MyRewardsRegistrationQuestionValue createRegistrationQuestionValues(int questionId, String name) {
        List<MyRewardsRegistrationQuestionValue> result = client.post(
                String.format("/api/v2/registration_questions/%d/list_of_values", questionId),
                Collections.singletonMap("name", name),
                MyRewardsRegistrationQuestionValue.LIST_TYPE_REFERENCE);
        return result.stream().filter(o -> o.getName().equals(name)).findAny().orElse(null);
    }

    /**
     * This endpoint creates a new site message for a user.
     * <p>
     * Site message are either plain text or HTML messages that appear in a User's message page. When a user logs in
     * they are notified of any unread messages via a pop-up (usually in the bottom right corner)
     *
     * @param userId  the ID of the user who will receive the message
     * @param content the content of message
     * @return the site message
     */
    public MyRewardsSiteMessage createSiteMessage(int userId, String content) {
        return client.post(
                String.format("/api/v2/users/%d/site_messages", userId),
                Collections.singletonMap("content", content),
                MyRewardsSiteMessage.class);
    }

    /**
     * This endpoint retrieves a specific site message.
     * <p>
     * Site message are either plain text or HTML messages that appear in a User's message page. When a user logs in
     * they are notified of any unread messages via a pop-up (usually in the bottom right corner)
     *
     * @param userId    the ID of the user who will receive the message
     * @param messageId the ID of the site message to retrieve
     * @return the site message
     */
    public MyRewardsSiteMessage getSiteMessage(int userId, int messageId) {
        return client.get(
                String.format("/api/v2/users/%d/site_messages/%d", userId, messageId),
                null,
                MyRewardsSiteMessage.class);
    }

    /**
     * This endpoint retrieves all site messages for a user.
     * <p>
     * Site message are either plain text or HTML messages that appear in a User's message page. When a user logs in
     * they are notified of any unread messages via a pop-up (usually in the bottom right corner)
     *
     * @param userId the ID of the user who will receive the message
     * @return the site message
     */
    public List<MyRewardsSiteMessage> getSiteMessages(int userId) {
        return client.get(
                String.format("/api/v2/users/%d/site_messages", userId),
                null,
                MyRewardsSiteMessage.LIST_TYPE_REFERENCE);
    }

    /**
     * This endpoint is designed to list all of a user’s transactions in json format. The points transactions will
     * be debits and credits with a description field.
     * <p>
     * All transactions listed in the response will be ordered so as to have the most recent transaction last in
     * the list.
     * <p>
     * Please remember that if a user transaction is performed on the MyRewards 2.0 platform since a request is made
     * over the api – this balance will be out of date.
     * <p>
     * On the server side a check will be made that the user_id you are requesting is
     *
     * <ul>
     *     <li>a valid user</li>
     *      <li>it is a user for the programme that your api key is scoped to.</li>
     * </ul>
     * That is to say that you can only retrieve transactions for users of your MyRewards Programme.
     * <p>
     * The remote_transaction_id is documented in the POST endpoint for creating transactions, this value is optional,
     * therefore can be null
     *
     * @param userId the ID of the user
     * @return a list of transactions for the user
     */
    public List<MyRewardsTransaction> getTransactions(int userId) {
        return client.get(
                String.format("/api/v2/users/%d/transactions", userId),
                null,
                MyRewardsTransaction.LIST_TYPE_REFERENCE);
    }

    /**
     * This endpoint is designed to show the latest transaction for the given user in json format. You can use this
     * endpoint to find the user's current balance.
     * <p>
     * Please remember that if a user transaction is performed on the MyRewards 2.0 platform since a request is made
     * over the api – this balance will be out of date.
     * <p>
     * On the server side a check will be made that the user_id you are requesting is
     *
     * <ul>
     *     <li>a valid user</li>
     *      <li>it is a user for the programme that your api key is scoped to.</li>
     * </ul>
     * That is to say that you can only retrieve transactions for users of your MyRewards Programme.
     * <p>
     * The remote_transaction_id is documented in the POST endpoint for creating transactions, this value is optional,
     * therefore can be null
     *
     * @param userId the ID of the user
     * @return a list of transactions for the user
     */
    public MyRewardsTransaction getLastTransactions(int userId) {
        return client.get(
                String.format("/api/v2/users/%d/transactions/last", userId),
                null,
                MyRewardsTransaction.class);
    }

    /**
     * A simple endpoint to fetch a list of user groups used for this programme. Only accessible if the key has been
     * granted access to users. Returns an array of user_groups as a flat list, each user group will indicate it's
     * parent id enabling the client end of the API to re-construct the user group hierarchy. This would be required
     * if a registration_question is linked to a tier of user_groups with a tier being a vertical 'slice' down the
     * hierarchy.
     *
     * @return the list of user groups
     */
    public List<MyRewardsUserGroup> getUserGroups() {
        return client.get(
                "/api/v2/user_groups",
                null,
                MyRewardsUserGroup.LIST_TYPE_REFERENCE);
    }

    /**
     * Get a user group with the matching id
     *
     * @param id the user group id
     * @return the user group
     */
    public MyRewardsUserGroup getUserGroup(int id) {
        return client.get(
                String.format("/api/v2/user_groups/%d", id),
                null,
                MyRewardsUserGroup.class);
    }

    /**
     * An endpoint to create a user group for this programme. Only accessible if the key has been granted access to
     * users. In order to create a usergroup a name is required. This usergroup can be nested underneath another user
     * group by passing the parent user group id. Images can be uploaded to the user group by passing a publicly
     * accessible image url. If no position is passed then it will default to the bottom of the hierachy.
     *
     * @param request the user group details to create
     * @return the list of user groups
     */
    public MyRewardsUserGroup createUserGroup(MyRewardsUserGroupRequest request) {
        return client.post(
                "/api/v2/user_groups",
                request,
                MyRewardsUserGroup.class);
    }

    /**
     * An endpoint to fetch a list of permissions for a given user_group. Returns an array of permissions with each
     * permission displaying its parent permission group name. This is to help identify different permissions when
     * names are the same across separate groups. It will also display whether the permission is active for a given
     * user_group.
     *
     * @param userGroupId the user group id
     * @return the list of user group permissions
     */
    public List<MyRewardsUserGroupPermission> getUserGroupPermissions(int userGroupId) {
        return client.get(
                String.format("/api/v2/user_groups/%d/permissions", userGroupId),
                null,
                MyRewardsUserGroupPermission.ListWrapper.class).getPermissions();
    }

    /**
     * An endpoint to update the permissions associated with a give user_group. After querying for the list of
     * permissions associated with a given user_group, you can add or remove a permission by simply changing the
     * value associated with the active key from true to false or vice-versa. You will be returned the updated
     * list of permissions relative to the given user_group
     * <p>
     * N.B. If updating a permission where the permission_group is not active the change will be ignored
     *
     * @param userGroupId the group id
     * @param request     the list of permissions to update
     * @return the updated permissions
     */
    public List<MyRewardsUserGroupPermission> setUserGroupPermissions(int userGroupId, List<MyRewardsUserGroupPermission> request) {
        return client.patch(
                String.format("/api/v2/user_groups/%d/permissions", userGroupId),
                new MyRewardsUserGroupPermission.ListWrapper(request),
                MyRewardsUserGroupPermission.ListWrapper.class).getPermissions();
    }

    /**
     * Fetch a user by id. This is currently using the update with no data to retrieve the user. Use at your own risk.
     *
     * @param userId the user's id
     * @return the user
     */
    public MyRewardsUser getUser(int userId) {
        return client.get(
                "/api/v3/users/" + userId,
                null,
                MyRewardsUser.class);
    }


    /**
     * In order to create a user account on the MyRewards 2.0 platform there is often some information about the user
     * we are creating that needs to be known before the account can be successfully created.
     * <p>
     * Firstly, the user group that the user will be created as a member of must be known, we provide an endpoint to
     * query the usergroups for your programme and if necessary to reconstruct the hierarchy for the usergroups see
     * the usergroups section.
     * <p>
     * Additionally, user accounts can have extra data required over and above the minimal default fields for a user
     * account. Typically, these take the form of employee data, membership number etc and can be defined as part of
     * your programme. These extra data are called registration_questions, for more information please see the
     * registration_questions section.
     * <p>
     * Telephone and mobile number fields must be supplied in international format, meaning starting with a '+'
     * followed by the international country code (I.e. the UK is 44) followed by at least 8 numeric characters.
     * <p>
     * Answers to the registration questions are provided in an array of objects, nested under the key
     * registration_answers_attributes. The nested objects themselves must have the keys registration_question_id
     * and answer.
     *
     * @param request the new user attributes
     * @return the created user
     */
    public MyRewardsUser createUser(MyRewardsUserRequest request) {
        return client.post(
                "/api/v3/users",
                request,
                MyRewardsUser.class);
    }


    /**
     * The update user api is available to update user information.
     *
     * @param userId  the id of the user to update
     * @param request the changed user attributes
     * @return the updated user
     */
    public MyRewardsUser updateUser(int userId, MyRewardsUserRequest request) {
        return client.patch(
                String.format("/api/v3/users/%d", userId),
                request,
                MyRewardsUser.class);
    }

    /**
     * This endpoint returns all companies associated to an api keys programme. Results are paginated and return 100
     * records per page and can be paged through by passing a page query parameter. If no page parameter is passed,
     * then page 1 with the first 100 records will be returned.
     *
     * @return the list of companies
     */
    public List<MyRewardsCompany> getCompanies(int page) {
        return client.get(
                "/api/v3/companies?page=" + page,
                null,
                MyRewardsCompany.LIST_TYPE_REFERENCE);
    }

    /**
     * This endpoint returns all companies, paging as necessary
     *
     * @return the list of companies
     */
    public List<MyRewardsCompany> getCompanies() {
        List<MyRewardsCompany> companies = new ArrayList<>();
        for (int  i = 1; true; i++) {
            List<MyRewardsCompany> page = getCompanies(i);
            companies.addAll(page);

            if (page.isEmpty() || page.size() < 100) {
                break;
            }
        }
        return companies;
    }

    /**
     * This endpoint returns a specific company associated to an api keys programme.
     *
     * @return the company matched by the identifier
     */
    public MyRewardsCompany getCompany(String identifier) {
        return client.get(
                "/api/v3/companies/" + URLEncoder.encode(identifier),
                //"/api/v3/companies/" + identifier,
                null,
                MyRewardsCompany.class);
    }

    /**
     * This endpoint returns a specific company associated to an api keys programme. This lists all and find the
     * matching id
     *
     * @return the company matched by the identifier
     */
    public MyRewardsCompany getCompany(int id) {
        return getCompanies().stream().filter(o -> o.getId() == id).findAny().orElse(null);
    }

    /**
     * This endpoint creates a new company associated to an api keys programme.
     *
     * @param request the request
     * @return the created company
     */
    public MyRewardsCompany createCompany(MyRewardsCompanyRequest request) {
        return client.post(
                "/api/v3/companies",
                request,
                MyRewardsCompany.class);
    }

    /**
     * This endpoint updates an existing company associated to an api keys programme.
     *
     * @param request the request
     * @param companyId The ID of the company to update
     * @return the created company
     */
    public MyRewardsCompany updateCompany(int companyId, MyRewardsCompanyRequest request) {
        return client.patch(
                String.format("/api/v3/companies/%d", companyId),
                request,
                MyRewardsCompany.class);
    }

    /**
     * This endpoint updates an existing company associated to an api keys programme.
     *
     * @param companyId The ID of the company to delete
     */
    public void deleteCompany(int companyId) {
        client.delete(
                String.format("/api/v3/companies/%d", companyId),
                null,
                String.class);
    }

    /**
     * An endpoint to fetch a list of permissions for a given user. Returns an array of permissions displaying it's
     * parent permissions group name. This is to help identify different permissions when names are the same across
     * separate groups. It will also display whether the permission is active for the given user and the state of the
     * permission which can be Same As User Group or if the permission has been overridden for that user: Always Allow
     * or Always Deny.
     *
     * @param userId the id of the user to update
     * @return the permissions for that user
     */
    public List<MyRewardsUserPermission> getUserPermissions(int userId) {
        return client.get(
                String.format("/api/v2/users/%d/permissions", userId),
                null,
                MyRewardsUserPermission.ListWrapper.class).getPermissions();
    }

    /**
     * A endpoint to update a list of permissions for a given user. Returns an array of permissions displaying it's
     * parent permissions group name. This is to help identify different permissions when names are the same across
     * separate groups. It will also display whether the permission is active for the given user and the state of the
     * permission which can be Same As User Group or if the permission has been overridden for that user: Always Allow
     * or Always Deny.
     * <p>
     * The only value that this request will change is the active field. The other fields are present to make it
     * easier to move from the GET request to a POST without having to reformat or delete much of the GET request
     * response.
     * <p>
     * N.B. When specifying active = true this will set the permission to "Always allow" setting to false will have
     * the effect of "Always deny"
     *
     * @param userId  the id of the user to update
     * @param request the permissions to update
     * @return the updated permissions
     */
    public List<MyRewardsUserPermission> setUserPermissions(int userId, List<MyRewardsUserPermission> request) {
        return client.patch(
                String.format("/api/v2/users/%d/permissions", userId),
                new MyRewardsUserPermission.ListWrapper(request),
                MyRewardsUserPermission.ListWrapper.class).getPermissions();
    }

    /**
     * Endpoint to fetch a list of the performance categories for a performance module enabled programme
     *
     * @return the performance categories
     */
    public List<MyRewardsPerformanceCategory> getPerformanceCategories() {
        return client.get(
                "/api/v2/performance/performance_categories",
                null,
                MyRewardsPerformanceCategory.LIST_TYPE_REFERENCE);
    }

    /**
     * Endpoint to create performance products for a programme with the performance module enabled.
     *
     * @return the products
     */
    public List<MyRewardsPerformanceProduct> createPerformanceProducts(List<MyRewardsPerformanceProductRequest> request) {
        return client.post(
                "/api/v2/performance/performance_product_batches",
                new MyRewardsPerformanceProductRequest.ListWrapper(request),
                MyRewardsPerformanceProduct.LIST_TYPE_REFERENCE);
    }

    /**
     * Endpoint to create performance products for a programme with the performance module enabled.
     *
     * @return the products
     */
    public List<MyRewardsPerformanceProduct> updatePerformanceProducts(List<MyRewardsPerformanceProductRequest> request) {
        return client.put(
                "/api/v2/performance/performance_product_batches",
                new MyRewardsPerformanceProductRequest.ListWrapper(request),
                MyRewardsPerformanceProduct.LIST_TYPE_REFERENCE);
    }

    /**
     * Endpoint to view allocated claims against a specific promotion. Claims can be scoped down via passed
     * in parameters. Only 100 records are returned at a time.
     *
     * @param request the search request
     * @return the allocated claims
     */
    public List<MyRewardsAllocatedClaim> getAllocatedClaims(MyRewardsAllocatedClaimsSearchRequest request) {
        return client.getWithBody(String.format("/api/v2/performance/promotions/%d/allocated_claims", request.getPromotionId()),
                request,
                MyRewardsAllocatedClaim.ListWrapper.class).getAllocatedClaims();
    }

    /**
     * Endpoint to view allocated claims against a specific promotion. Paginates until all claims are fetched
     *
     * @param request the search request
     * @return the allocated claims
     */
    public List<MyRewardsAllocatedClaim> getAllAllocatedClaims(MyRewardsAllocatedClaimsSearchRequest request) {
        List<MyRewardsAllocatedClaim> claims = new ArrayList<>();
        for (int  i = 1; true; i++) {

            List<MyRewardsAllocatedClaim> page = getAllocatedClaims(request.withPage(i));
            claims.addAll(page);

            if (page.isEmpty() || page.size() < 100) {
                break;
            }
        }
        return claims;
    }

    /**
     * Endpoint to create allocated claims against specific promotion. Claims are created by using the
     * data_field names from above as key/value pairs. Your request must include user_group_id or company_id,
     * depending on the promotion configuration.
     *
     * @param promotionId the ID of the promotion claim is for.
     * @param request the claims to submit
     * @return the created values
     */
    public List<MyRewardsAllocatedClaim> createAllocatedClaims(int promotionId, List<MyRewardsCreateAllocatedClaimRequest> request) {
        return client.post(String.format("/api/v2/performance/promotions/%d/allocated_claims", promotionId),
                new MyRewardsCreateAllocatedClaimRequest.ListWrapper(request),
                MyRewardsAllocatedClaim.ListWrapper.class).getAllocatedClaims();
    }

    /**
     * Endpoint to decline a given allocated claim.
     *
     * @param promotionId the ID of the promotion claim is for.
     * @param claimId the ID of the claim to decline.
     * @param request the decline data
     */
    public void declineAllocatedClaims(int promotionId, int claimId, MyRewardsDeclineAllocatedClaimRequest request) {
        client.post(String.format("/api/v2/performance/promotions/%d/allocated_claims/%d/decline", promotionId, claimId),
                request,
                Void.class);
    }

    /**
     * A Data Widget is used to show user specific information or data uploaded into the data widgets area.
     * <p>
     * This endpoint retrieves all data_widgets that belong to a programme (scoped by api key)
     *
     * @return all the available data widget
     */
    public List<MyRewardsDataWidget> getDataWidgets() {
        return client.get(
                "/api/v2/data_widgets",
                null,
                MyRewardsDataWidget.LIST_TYPE_REFERENCE);
    }

    /**
     * Returns the specific data_widget and user_data_widget data for the user specified userId and the widgetId
     * specified.
     *
     * @param userId   the ID of the user you want to update data for
     * @param widgetId the ID of the data_widget to retrieve
     * @return the users widget
     */
    public MyRewardsDataWidget getUserDataWidget(int userId, int widgetId) {
        return client.get(
                String.format("/api/v2/users/%d/data_widgets/%d", userId, widgetId),
                null,
                MyRewardsDataWidget.class);
    }

    /**
     * Update the data of the specific user_data_widget for the user specified { user_id } and the { id } specified.
     *
     * @param userId   the ID of the user you want to update data for
     * @param widgetId the ID of the data_widget to retrieve
     * @param data     your desired data string
     * @return the updated widget
     */
    public MyRewardsDataWidget updateUserDataWidget(int userId, int widgetId, String data) {
        return client.get(
                String.format("/api/v2/users/%d/data_widgets/%d", userId, widgetId),
                Collections.singletonMap("data", data),
                MyRewardsDataWidget.class);
    }
}
