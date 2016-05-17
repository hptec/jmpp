package cn.cerestech.framework.core.date;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.beust.jcommander.internal.Lists;

/**
 * 多个时间段
 * 
 * @author harryhe
 *
 */
public class MultipleTimeRange {

	private List<TimeRange> rangeList = Lists.newArrayList();

	public static MultipleTimeRange of(TimeRange... ranges) {
		MultipleTimeRange mRange = new MultipleTimeRange();
		for (TimeRange r : ranges) {
			mRange.put(r);
		}
		return mRange;
	}

	public MultipleTimeRange put(TimeRange range) {
		rangeList.add(range);
		return this;
	}

	public MultipleTimeRange put(Calendar from, Calendar to) {
		return put(TimeRange.from(from, to));
	}

	public MultipleTimeRange put(Date from, Date to) {
		return put(TimeRange.from(from, to));
	}

	public MultipleTimeRange put(Long from, Long to) {
		return put(TimeRange.from(from, to));
	}

	public Boolean inRange(Calendar timePoint) {
		for (TimeRange r : rangeList) {
			if (r.inRange(timePoint)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
