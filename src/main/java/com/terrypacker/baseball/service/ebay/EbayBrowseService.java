package com.terrypacker.baseball.service.ebay;

import com.terrypacker.ebay.browse.ApiClient;
import com.terrypacker.ebay.browse.api.ItemSummaryApi;
import com.terrypacker.ebay.browse.models.SearchPagedCollection;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Terry Packer
 */
public class EbayBrowseService {
    private final ApiClient browseApiClient;
    public EbayBrowseService(@Value("terrypacker.ebay.api.baseurl") String ebayApiBaseUrl) {
        this.browseApiClient = new ApiClient();
        this.browseApiClient.setBasePath(ebayApiBaseUrl);
    }

    /**
     * Search the API for stuff
     * @param query
     * @param limit
     * @param offset
     * @return
     */
    public SearchPagedCollection browse(String query, int limit, int offset) {
        ItemSummaryApi itemSummaryApi = new ItemSummaryApi(browseApiClient);
        EbayItemSummarySearchQuery searchQuery = new EbayItemSummarySearchQuery(query, limit, offset);

        return itemSummaryApi.search(
            searchQuery.getAspectFilter(),
            searchQuery.getAutoCorrect(),
            searchQuery.getCategoryIds(),
            searchQuery.getCharityIds(),
            searchQuery.getCompatibilityFilter(),
            searchQuery.getEpid(),
            searchQuery.getFieldgroups(),
            searchQuery.getFilter(),
            searchQuery.getGtin(),
            searchQuery.getLimit(),
            searchQuery.getOffset(),
            searchQuery.getQ(),
            searchQuery.getSort(),
            searchQuery.getX_EBAY_C_ENDUSERCTX(),
            searchQuery.getX_EBAY_C_MARKETPLACE_ID(),
            searchQuery.getAcceptLanguage());

    }

    /**
     * @See com.terrypacker.ebay.browse.api.ItemSummaryApi
     */
    private static class EbayItemSummarySearchQuery {

        String aspectFilter;
        String autoCorrect;
        String categoryIds;
        String charityIds;
        String compatibilityFilter;
        String epid;
        String fieldgroups;
        String filter;
        String gtin;
        String limit;
        String offset;
        String q;
        String sort;
        String X_EBAY_C_ENDUSERCTX;
        String X_EBAY_C_MARKETPLACE_ID;
        String acceptLanguage;


        public EbayItemSummarySearchQuery(String query, Integer limit, Integer offset) {
            this.filter = query;
            if(limit != null) {
                this.limit = limit.toString();
            }
            if(offset != null) {
                this.offset = offset.toString();
            }
        }

        public String getAspectFilter() {
            return aspectFilter;
        }

        public void setAspectFilter(String aspectFilter) {
            this.aspectFilter = aspectFilter;
        }

        public String getAutoCorrect() {
            return autoCorrect;
        }

        public void setAutoCorrect(String autoCorrect) {
            this.autoCorrect = autoCorrect;
        }

        public String getCategoryIds() {
            return categoryIds;
        }

        public void setCategoryIds(String categoryIds) {
            this.categoryIds = categoryIds;
        }

        public String getCharityIds() {
            return charityIds;
        }

        public void setCharityIds(String charityIds) {
            this.charityIds = charityIds;
        }

        public String getCompatibilityFilter() {
            return compatibilityFilter;
        }

        public void setCompatibilityFilter(String compatibilityFilter) {
            this.compatibilityFilter = compatibilityFilter;
        }

        public String getEpid() {
            return epid;
        }

        public void setEpid(String epid) {
            this.epid = epid;
        }

        public String getFieldgroups() {
            return fieldgroups;
        }

        public void setFieldgroups(String fieldgroups) {
            this.fieldgroups = fieldgroups;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public String getGtin() {
            return gtin;
        }

        public void setGtin(String gtin) {
            this.gtin = gtin;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getX_EBAY_C_ENDUSERCTX() {
            return X_EBAY_C_ENDUSERCTX;
        }

        public void setX_EBAY_C_ENDUSERCTX(String x_EBAY_C_ENDUSERCTX) {
            X_EBAY_C_ENDUSERCTX = x_EBAY_C_ENDUSERCTX;
        }

        public String getX_EBAY_C_MARKETPLACE_ID() {
            return X_EBAY_C_MARKETPLACE_ID;
        }

        public void setX_EBAY_C_MARKETPLACE_ID(String x_EBAY_C_MARKETPLACE_ID) {
            X_EBAY_C_MARKETPLACE_ID = x_EBAY_C_MARKETPLACE_ID;
        }

        public String getAcceptLanguage() {
            return acceptLanguage;
        }

        public void setAcceptLanguage(String acceptLanguage) {
            this.acceptLanguage = acceptLanguage;
        }
    }

}
