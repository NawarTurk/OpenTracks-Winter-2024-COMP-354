package de.dennisguse.opentracks.ui.aggregatedStatistics;

import static java.lang.Math.round;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dennisguse.opentracks.data.models.Distance;
import de.dennisguse.opentracks.data.models.Speed;
import de.dennisguse.opentracks.data.models.Track;
import de.dennisguse.opentracks.stats.MockupData;
import de.dennisguse.opentracks.stats.TrackStatistics;

public class AggregatedStatistics {

    private final Map<String, AggregatedStatistic> dataMap = new HashMap<>();

    private final List<AggregatedStatistic> dataList = new ArrayList<>();

    public AggregatedStatistics(@NonNull List<Track> tracks) {
        for (Track track : tracks) {
            aggregate(track);
        }
        MockupData mockupData = new MockupData();
        List<TrackStatistics> trackStatistics = mockupData.getTrackStatistics();
        //int getTotalRuns = trackStatistics.get(0).getTotalRunsSeason();
        //int getTotalRuns2 = trackStatistics.get(1).getTotalRunsSeason();
        dataList.addAll(dataMap.values());
        dataList.sort((o1, o2) -> {
            if (o1.getCountTracks() == o2.getCountTracks()) {
                return o1.getActivityTypeLocalized().compareTo(o2.getActivityTypeLocalized());
            }
            return (o1.getCountTracks() < o2.getCountTracks() ? 1 : -1);
        });
    }

    public AggregatedStatistics(@NonNull List<Track> tracks, Boolean isDaily) {
        for (Track track : tracks) {
            if (isDaily) {
                aggregateDays(track);
            } else {
                aggregate(track);
            }
        }

        if(isDaily) {
            dataList.addAll(dataMap.values());
            dataList.sort((o1, o2) -> {
                if (o1.getCountTracks() == o2.getCountTracks()) {
                    return o1.getDay().compareTo(o2.getDay());
                }
                return (o1.getCountTracks() < o2.getCountTracks() ? 1 : -1);
            });
        } else {
            dataList.addAll(dataMap.values());
            dataList.sort((o1, o2) -> {
                if (o1.getCountTracks() == o2.getCountTracks()) {
                    return o1.getActivityTypeLocalized().compareTo(o2.getActivityTypeLocalized());
                }
                return (o1.getCountTracks() < o2.getCountTracks() ? 1 : -1);
            });
        }
    }

    @VisibleForTesting
    public void aggregate(@NonNull Track track) {
        String activityTypeLocalized = track.getActivityTypeLocalized();
        if (dataMap.containsKey(activityTypeLocalized)) {
            dataMap.get(activityTypeLocalized).add(track.getTrackStatistics());
        } else {
            dataMap.put(activityTypeLocalized,
                    new AggregatedStatistic(activityTypeLocalized, track.getTrackStatistics()));
        }
    }

    @VisibleForTesting
    public void aggregateDays(@NonNull Track track) {
        String activityTypeLocalized = track.getActivityTypeLocalized();
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d yyyy");
        String day = formatter.format(Date.from(track.getTrackStatistics().getStopTime()));
        String combinedKey = day + " - " + activityTypeLocalized;
        if (dataMap.containsKey(combinedKey)) {
            dataMap.get(combinedKey).add(track.getTrackStatistics());
        } else {
            dataMap.put(combinedKey, new AggregatedStatistic(track.getActivityTypeLocalized(), track.getTrackStatistics(), day));
        }
    }

    public int getCount() {
        return dataMap.size();
    }

    public AggregatedStatistic get(String activityType) {
        return dataMap.get(activityType);
    }

    public AggregatedStatistic getItem(int position) {
        return dataList.get(position);
    }

    public static class AggregatedStatistic {
        private final String activityTypeLocalized;

        private String day;
        private final TrackStatistics trackStatistics;
        private int countTracks = 1;

        // My code
        private ArrayList<TrackStatistics> listOfTracks;

        public AggregatedStatistic(String activityTypeLocalized, TrackStatistics trackStatistics) {
            this.activityTypeLocalized = activityTypeLocalized;
            this.trackStatistics = trackStatistics;

            // My code
            this.listOfTracks = new ArrayList<TrackStatistics>();
            this.listOfTracks.add(trackStatistics);

        }

        public AggregatedStatistic(String activityTypeLocalized, TrackStatistics trackStatistics, String day) {
            this.day = day;
            this.activityTypeLocalized = activityTypeLocalized;
            this.trackStatistics = trackStatistics;
        }

        public String getActivityTypeLocalized() {
            return activityTypeLocalized;
        }

        public String getDay() {
            return day;
        }

        public TrackStatistics getTrackStatistics() {
            return trackStatistics;
        }

        public int getCountTracks() {
            return countTracks;
        }

        void add(TrackStatistics statistics) {
            trackStatistics.merge(statistics);
            countTracks++;

            // My code
            listOfTracks.add(statistics);
        }

        public String getTotalTime() {
            Duration duration = trackStatistics.getTotalTime();
            String formattedTime = formatDuration(duration);
            return formattedTime;
        }

        public String getMovingTime() {
            Duration duration = trackStatistics.getMovingTime();
            String formattedTime = formatDuration(duration);
            return formattedTime;
        }

        public Distance getTotalDistance() {
            return trackStatistics.getTotalDistance();
        }

        public Speed getMaxSpeed() {
            return trackStatistics.getMaxSpeed();

        }

        public double getMaxVertical() {
            boolean hasMaxAlt = trackStatistics.hasAltitudeMax();
            if(hasMaxAlt) {
                double value = trackStatistics.getMaxAltitude();
                double roundedValue = round(value, 2);
                return roundedValue;
            }
            return 0;
        }

        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();
            System.out.println("valueeeeee:, " + value);
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        private String formatDuration(Duration duration) {
            long seconds = duration.getSeconds();
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;

            // Format hours, minutes and seconds to ensure they are in 00:00:00 format
            return String.format("%02d:%02d:%02d", hours, minutes, secs);
        }

        // My code
        public ArrayList<TrackStatistics> getListOfTracks() {return listOfTracks;}
    }
}
