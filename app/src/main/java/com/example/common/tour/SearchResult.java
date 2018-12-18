package com.example.common.tour;

import java.util.List;

public class SearchResult {
    private int resultCount;

    private List<Results> results ;

    public void setResultCount(int resultCount){
        this.resultCount = resultCount;
    }
    public int getResultCount(){
        return this.resultCount;
    }
    public void setResults(List<Results> results){
        this.results = results;
    }
    public List<Results> getResults(){
        return this.results;
    }
    public class Results {
        private String wrapperType;

        private String kind;

        private int collectionId;

        private int trackId;

        private String artistName;

        private String collectionName;

        private String trackName;

        private String collectionCensoredName;

        private String trackCensoredName;

        private String collectionViewUrl;

        private String feedUrl;

        private String trackViewUrl;

        private String artworkUrl30;

        private String artworkUrl60;

        private String artworkUrl100;

        private int collectionPrice;

        private int trackPrice;

        private int trackRentalPrice;

        private int collectionHdPrice;

        private int trackHdPrice;

        private int trackHdRentalPrice;

        private String releaseDate;

        private String collectionExplicitness;

        private String trackExplicitness;

        private int trackCount;

        private String country;

        private String currency;

        private String primaryGenreName;

        private String contentAdvisoryRating;

        private String artworkUrl600;

        private List<String> genreIds ;

        private List<String> genres ;

        public void setWrapperType(String wrapperType){
            this.wrapperType = wrapperType;
        }
        public String getWrapperType(){
            return this.wrapperType;
        }
        public void setKind(String kind){
            this.kind = kind;
        }
        public String getKind(){
            return this.kind;
        }
        public void setCollectionId(int collectionId){
            this.collectionId = collectionId;
        }
        public int getCollectionId(){
            return this.collectionId;
        }
        public void setTrackId(int trackId){
            this.trackId = trackId;
        }
        public int getTrackId(){
            return this.trackId;
        }
        public void setArtistName(String artistName){
            this.artistName = artistName;
        }
        public String getArtistName(){
            return this.artistName;
        }
        public void setCollectionName(String collectionName){
            this.collectionName = collectionName;
        }
        public String getCollectionName(){
            return this.collectionName;
        }
        public void setTrackName(String trackName){
            this.trackName = trackName;
        }
        public String getTrackName(){
            return this.trackName;
        }
        public void setCollectionCensoredName(String collectionCensoredName){
            this.collectionCensoredName = collectionCensoredName;
        }
        public String getCollectionCensoredName(){
            return this.collectionCensoredName;
        }
        public void setTrackCensoredName(String trackCensoredName){
            this.trackCensoredName = trackCensoredName;
        }
        public String getTrackCensoredName(){
            return this.trackCensoredName;
        }
        public void setCollectionViewUrl(String collectionViewUrl){
            this.collectionViewUrl = collectionViewUrl;
        }
        public String getCollectionViewUrl(){
            return this.collectionViewUrl;
        }
        public void setFeedUrl(String feedUrl){
            this.feedUrl = feedUrl;
        }
        public String getFeedUrl(){
            return this.feedUrl;
        }
        public void setTrackViewUrl(String trackViewUrl){
            this.trackViewUrl = trackViewUrl;
        }
        public String getTrackViewUrl(){
            return this.trackViewUrl;
        }
        public void setArtworkUrl30(String artworkUrl30){
            this.artworkUrl30 = artworkUrl30;
        }
        public String getArtworkUrl30(){
            return this.artworkUrl30;
        }
        public void setArtworkUrl60(String artworkUrl60){
            this.artworkUrl60 = artworkUrl60;
        }
        public String getArtworkUrl60(){
            return this.artworkUrl60;
        }
        public void setArtworkUrl100(String artworkUrl100){
            this.artworkUrl100 = artworkUrl100;
        }
        public String getArtworkUrl100(){
            return this.artworkUrl100;
        }
        public void setCollectionPrice(int collectionPrice){
            this.collectionPrice = collectionPrice;
        }
        public int getCollectionPrice(){
            return this.collectionPrice;
        }
        public void setTrackPrice(int trackPrice){
            this.trackPrice = trackPrice;
        }
        public int getTrackPrice(){
            return this.trackPrice;
        }
        public void setTrackRentalPrice(int trackRentalPrice){
            this.trackRentalPrice = trackRentalPrice;
        }
        public int getTrackRentalPrice(){
            return this.trackRentalPrice;
        }
        public void setCollectionHdPrice(int collectionHdPrice){
            this.collectionHdPrice = collectionHdPrice;
        }
        public int getCollectionHdPrice(){
            return this.collectionHdPrice;
        }
        public void setTrackHdPrice(int trackHdPrice){
            this.trackHdPrice = trackHdPrice;
        }
        public int getTrackHdPrice(){
            return this.trackHdPrice;
        }
        public void setTrackHdRentalPrice(int trackHdRentalPrice){
            this.trackHdRentalPrice = trackHdRentalPrice;
        }
        public int getTrackHdRentalPrice(){
            return this.trackHdRentalPrice;
        }
        public void setReleaseDate(String releaseDate){
            this.releaseDate = releaseDate;
        }
        public String getReleaseDate(){
            return this.releaseDate;
        }
        public void setCollectionExplicitness(String collectionExplicitness){
            this.collectionExplicitness = collectionExplicitness;
        }
        public String getCollectionExplicitness(){
            return this.collectionExplicitness;
        }
        public void setTrackExplicitness(String trackExplicitness){
            this.trackExplicitness = trackExplicitness;
        }
        public String getTrackExplicitness(){
            return this.trackExplicitness;
        }
        public void setTrackCount(int trackCount){
            this.trackCount = trackCount;
        }
        public int getTrackCount(){
            return this.trackCount;
        }
        public void setCountry(String country){
            this.country = country;
        }
        public String getCountry(){
            return this.country;
        }
        public void setCurrency(String currency){
            this.currency = currency;
        }
        public String getCurrency(){
            return this.currency;
        }
        public void setPrimaryGenreName(String primaryGenreName){
            this.primaryGenreName = primaryGenreName;
        }
        public String getPrimaryGenreName(){
            return this.primaryGenreName;
        }
        public void setContentAdvisoryRating(String contentAdvisoryRating){
            this.contentAdvisoryRating = contentAdvisoryRating;
        }
        public String getContentAdvisoryRating(){
            return this.contentAdvisoryRating;
        }
        public void setArtworkUrl600(String artworkUrl600){
            this.artworkUrl600 = artworkUrl600;
        }
        public String getArtworkUrl600(){
            return this.artworkUrl600;
        }
        public void setStringId(List<String> genreIds){
            this.genreIds = genreIds;
        }
        public List<String> getStringId(){
            return this.genreIds;
        }
        public void setString(List<String> genres){
            this.genres = genres;
        }
        public List<String> getString(){
            return this.genres;
        }

    }
}
