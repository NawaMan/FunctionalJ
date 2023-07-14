// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types;

/**
 * This class purpose is to allow decoupling of the annotation projects and the core project.
 * So that the user of the annotation project does not have to include the core in the annotation processing.
 * At the same time provide the safety net check (at least for a developer and as a early alarm)
 *   that there is a mismatch of class names needed by the annotation processor.
 *
 * The mechanism is to have this class that list all full name of classes (in the core project)
 *   that this project thinks suppose to be like.
 * Then, a test in the core project confirm that assumption.
 * This way, as the whole FunctionJ is built, the assumption check.
 * This does not prevent the problem when different version of the annotation processor and core is used at runtime.
 * But some check is better than nothing.
 * And if that happens (the class name change),
 *   it will be a broken change which should be communicate differently.
 *
 * This whole mess is from the fact that the interface in the core (those lens classes)
 *   which is support to just define the possible operation and data type now contains the implementation.
 * Until the better solution comes along, Let's do this for now.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public enum Core {

    BigDecimalLens("BigDecimalLens"),
    BigIntegerLens("BigIntegerLens"),
    BooleanLens("BooleanLens"),
    DoubleLens("DoubleLens"),
    FuncListLens("FuncListLens"),
    FuncMapLens("FuncMapLens"),
    IntegerLens("IntegerLens"),
    ListLens("ListLens"),
    LongLens("LongLens"),
    MapLens("MapLens"),
    MayBeLens("MayBeLens"),
    NullableLens("NullableLens"),
    ObjectLens("ObjectLens"),
    ObjectLensImpl("ObjectLensImpl"),
    OptionalLens("OptionalLens"),
    StringLens("StringLens"),
    Generated("Generated", "functionalj.types"),
    LensSpec("LensSpec", "functionalj.lens.core"),
    FuncList("FuncList", "functionalj.list"),
    FuncMap("FuncMap", "functionalj.map"),
    ImmutableFuncList("ImmutableFuncList", "functionalj.list"),
    ImmutableFuncMap("ImmutableFuncMap", "functionalj.map"),
    Nullable("Nullable", "nullablej.nullable"),
    Optional("Optional", "java.util"),
    Pipeable("Pipeable", "functionalj.pipeable"),
    DayOfWeek("DayOfWeek", "java.time"),
    Duration("Duration", "java.time"),
    Instant("Instant", "java.time"),
    LocalDate("LocalDate", "java.time"),
    LocalDateTime("LocalDateTime", "java.time"),
    LocalTime("LocalTime", "java.time"),
    Month("Month", "java.time"),
    OffsetDateTime("OffsetDateTime", "java.time"),
    Period("Period", "java.time"),
    ZonedDateTime("ZonedDateTime", "java.time"),
    ZoneId("ZoneId", "java.time"),
    ZoneOffset("ZoneOffset", "java.time"),
    ZoneOffsetTransition("ZoneOffsetTransition", "java.time"),
    ZoneOffsetTransitionRule("ZoneOffsetTransitionRule", "java.time"),
    DayOfWeekLens("DayOfWeekLens", "functionalj.lens.lenses.java.time"),
    DurationLens("DurationLens", "functionalj.lens.lenses.java.time"),
    InstantLens("InstantLens", "functionalj.lens.lenses.java.time"),
    LocalDateLens("LocalDateLens", "functionalj.lens.lenses.java.time"),
    LocalDateTimeLens("LocalDateTimeLens", "functionalj.lens.lenses.java.time"),
    LocalTimeLens("LocalTimeLens", "functionalj.lens.lenses.java.time"),
    MonthLens("MonthLens", "functionalj.lens.lenses.java.time"),
    OffsetDateTimeLens("OffsetDateTimeLens", "functionalj.lens.lenses.java.time"),
    PeriodLens("PeriodLens", "functionalj.lens.lenses.java.time"),
    ZonedDateTimeLens("ZonedDateTimeLens", "functionalj.lens.lenses.java.time"),
    ZonedIdLens("ZonedIdLens", "functionalj.lens.lenses.java.time"),
    ZonedOffsetLens("ZonedOffsetLens", "functionalj.lens.lenses.java.time"),
    ZonedOffsetTransitionLens("ZonedOffsetTransitionLens", "functionalj.lens.lenses.java.time"),
    ZonedOffsetTransitionRuleLens("ZonedOffsetTransitionRuleLens", "functionalj.lens.lenses.java.time"),
    StrFunc("StrFuncs", "functionalj.functions");

    private static final String LENSES_PACKAGE = "functionalj.lens.lenses";

    private String simpleName;

    private String packageName;

    private Type type = null;

    private Core(String simpleName) {
        this(simpleName, LENSES_PACKAGE);
    }

    private Core(String simpleName, String packageName) {
        this.simpleName = simpleName;
        this.packageName = packageName;
    }

    public String simpleName() {
        return simpleName;
    }

    public String packageName() {
        return packageName;
    }

    public Type type() {
        if (type == null)
            type = new Type(packageName, simpleName);
        return type;
    }
}
