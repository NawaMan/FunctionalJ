Duration threshold = Duration.ofMinutes(30);

// Sort by timestamp first if not already sorted
FuncList<YourObject> sortedList = list.sortedBy(obj -> obj.getActionTimestamp());

// Group actions that are within threshold of each other
FuncList<FuncList<YourObject>> segments = sortedList.segmentBetween(
    // Start new segment when gap is too large
    (current, next) -> Duration.between(
        current.getActionTimestamp(), 
        next.getActionTimestamp()
    ).compareTo(threshold) > 0
);

// Get gaps between segments
FuncList<TimeGap> gaps = segments.pairs()
    .map(segmentPair -> new TimeGap(
        segmentPair.first().last().getActionTimestamp(),
        segmentPair.second().first().getActionTimestamp()
    ));