package com.example.common.first;

import java.util.Date;
import java.util.List;

public class Top5 {

        private int resultCount;
        private List<Results> results;
        public void setResultCount(int resultCount) {
            this.resultCount = resultCount;
        }
        public int getResultCount() {
            return resultCount;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }
        public List<Results> getResults() {
            return results;
        }
    public class Results {

        private String wrapperType;
        private String kind;
        private long artistId;
        private long collectionId;
        private long trackId;
        private String artistName;
        private String collectionName;
        private String trackName;
        private String collectionCensoredName;
        private String trackCensoredName;
        private String artistViewUrl;
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
        private Date releaseDate;
        private String collectionExplicitness;
        private String trackExplicitness;
        private int trackCount;
        private String country;
        private String currency;
        private String primaryGenreName;
        private String artworkUrl600;
        private List<String> genreIds;
        private List<String> genres;
        public void setWrapperType(String wrapperType) {
            this.wrapperType = wrapperType;
        }
        public String getWrapperType() {
            return wrapperType;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }
        public String getKind() {
            return kind;
        }

        public void setArtistId(long artistId) {
            this.artistId = artistId;
        }
        public long getArtistId() {
            return artistId;
        }

        public void setCollectionId(long collectionId) {
            this.collectionId = collectionId;
        }
        public long getCollectionId() {
            return collectionId;
        }

        public void setTrackId(long trackId) {
            this.trackId = trackId;
        }
        public long getTrackId() {
            return trackId;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }
        public String getArtistName() {
            return artistName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }
        public String getCollectionName() {
            return collectionName;
        }

        public void setTrackName(String trackName) {
            this.trackName = trackName;
        }
        public String getTrackName() {
            return trackName;
        }

        public void setCollectionCensoredName(String collectionCensoredName) {
            this.collectionCensoredName = collectionCensoredName;
        }
        public String getCollectionCensoredName() {
            return collectionCensoredName;
        }

        public void setTrackCensoredName(String trackCensoredName) {
            this.trackCensoredName = trackCensoredName;
        }
        public String getTrackCensoredName() {
            return trackCensoredName;
        }

        public void setArtistViewUrl(String artistViewUrl) {
            this.artistViewUrl = artistViewUrl;
        }
        public String getArtistViewUrl() {
            return artistViewUrl;
        }

        public void setCollectionViewUrl(String collectionViewUrl) {
            this.collectionViewUrl = collectionViewUrl;
        }
        public String getCollectionViewUrl() {
            return collectionViewUrl;
        }

        public void setFeedUrl(String feedUrl) {
            this.feedUrl = feedUrl;
        }
        public String getFeedUrl() {
            return feedUrl;
        }

        public void setTrackViewUrl(String trackViewUrl) {
            this.trackViewUrl = trackViewUrl;
        }
        public String getTrackViewUrl() {
            return trackViewUrl;
        }

        public void setArtworkUrl30(String artworkUrl30) {
            this.artworkUrl30 = artworkUrl30;
        }
        public String getArtworkUrl30() {
            return artworkUrl30;
        }

        public void setArtworkUrl60(String artworkUrl60) {
            this.artworkUrl60 = artworkUrl60;
        }
        public String getArtworkUrl60() {
            return artworkUrl60;
        }

        public void setArtworkUrl100(String artworkUrl100) {
            this.artworkUrl100 = artworkUrl100;
        }
        public String getArtworkUrl100() {
            return artworkUrl100;
        }

        public void setCollectionPrice(int collectionPrice) {
            this.collectionPrice = collectionPrice;
        }
        public int getCollectionPrice() {
            return collectionPrice;
        }

        public void setTrackPrice(int trackPrice) {
            this.trackPrice = trackPrice;
        }
        public int getTrackPrice() {
            return trackPrice;
        }

        public void setTrackRentalPrice(int trackRentalPrice) {
            this.trackRentalPrice = trackRentalPrice;
        }
        public int getTrackRentalPrice() {
            return trackRentalPrice;
        }

        public void setCollectionHdPrice(int collectionHdPrice) {
            this.collectionHdPrice = collectionHdPrice;
        }
        public int getCollectionHdPrice() {
            return collectionHdPrice;
        }

        public void setTrackHdPrice(int trackHdPrice) {
            this.trackHdPrice = trackHdPrice;
        }
        public int getTrackHdPrice() {
            return trackHdPrice;
        }

        public void setTrackHdRentalPrice(int trackHdRentalPrice) {
            this.trackHdRentalPrice = trackHdRentalPrice;
        }
        public int getTrackHdRentalPrice() {
            return trackHdRentalPrice;
        }

        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }
        public Date getReleaseDate() {
            return releaseDate;
        }

        public void setCollectionExplicitness(String collectionExplicitness) {
            this.collectionExplicitness = collectionExplicitness;
        }
        public String getCollectionExplicitness() {
            return collectionExplicitness;
        }

        public void setTrackExplicitness(String trackExplicitness) {
            this.trackExplicitness = trackExplicitness;
        }
        public String getTrackExplicitness() {
            return trackExplicitness;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }
        public int getTrackCount() {
            return trackCount;
        }

        public void setCountry(String country) {
            this.country = country;
        }
        public String getCountry() {
            return country;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
        public String getCurrency() {
            return currency;
        }

        public void setPrimaryGenreName(String primaryGenreName) {
            this.primaryGenreName = primaryGenreName;
        }
        public String getPrimaryGenreName() {
            return primaryGenreName;
        }

        public void setArtworkUrl600(String artworkUrl600) {
            this.artworkUrl600 = artworkUrl600;
        }
        public String getArtworkUrl600() {
            return artworkUrl600;
        }

        public void setGenreIds(List<String> genreIds) {
            this.genreIds = genreIds;
        }
        public List<String> getGenreIds() {
            return genreIds;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }
        public List<String> getGenres() {
            return genres;
        }

    }
}
