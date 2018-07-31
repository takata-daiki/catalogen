package dmonner.xlbp.stat;

import java.io.IOException;
import java.util.Map;

import dmonner.xlbp.util.CSVWriter;

public class SetStat
{
	private final String name;
	private final TargetSetStat targets;
	private final FractionStat stepsCorrect;
	private final FractionStat trialsCorrect;

	public SetStat()
	{
		this("");
	}

	public SetStat(final String name)
	{
		this.name = name;
		this.targets = new TargetSetStat();
		this.stepsCorrect = new FractionStat("Steps");
		this.trialsCorrect = new FractionStat("Trials");
	}

	public SetStat(final String name, final Iterable<TrialStat> evaluations)
	{
		this(name);

		for(final TrialStat eval : evaluations)
			add(eval);

		analyze();
	}

	public SetStat(final String name, final TrialStat[] evaluations)
	{
		this(name);

		for(final TrialStat eval : evaluations)
			add(eval);

		analyze();
	}

	public void add(final SetStat that)
	{
		targets.add(that.targets);
		stepsCorrect.add(that.stepsCorrect);
		trialsCorrect.add(that.trialsCorrect);
	}

	public void add(final TrialStat that)
	{
		targets.add(that.getTargets());
		stepsCorrect.add(that.getStepsCorrect());
		trialsCorrect.add(that.getTrialCorrect());
	}

	public void addTo(final Map<String, Object> map)
	{
		map.put("SetName", name);
		trialsCorrect.addTo(map);
		stepsCorrect.addTo(map);
		targets.addTo(map);
	}

	public void analyze()
	{
		targets.analyze();
		stepsCorrect.analyze();
		trialsCorrect.analyze();
	}

	public void clear()
	{
		targets.clear();
		stepsCorrect.clear();
		trialsCorrect.clear();
	}

	public float getAccuracy()
	{
		return targets.getBits().getAccuracy();
	}

	public void saveData(final CSVWriter out) throws IOException
	{
		out.beginRecord();
		out.appendField(name);
		trialsCorrect.saveData(out);
		stepsCorrect.saveData(out);
		targets.saveData(out);
	}

	public void saveHeader(final CSVWriter out) throws IOException
	{
		out.beginRecord();
		out.appendHeader("name");
		trialsCorrect.saveHeader(out);
		stepsCorrect.saveHeader(out);
		targets.saveHeader(out);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("SetName = ");
		sb.append(name);
		sb.append("\n");

		sb.append(trialsCorrect.toString());
		sb.append(stepsCorrect.toString());
		sb.append(targets.toString());

		return sb.toString();
	}
}
