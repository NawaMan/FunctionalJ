// TODO - Think about this again.
// package functionalj.lens.lenses;
// 
// import java.util.function.Function;
// 
// import functionalj.types.IRule;
// import functionalj.lens.core.LensSpec;
// import functionalj.lens.core.LensSpecParameterized;
// import functionalj.lens.core.LensUtils;
// import functionalj.lens.core.WriteLens;
// import functionalj.result.Result;
// 
// 
// public class RuleLens<HOST, SUB, SUBLENS extends AnyLens<HOST, SUB>> implements ResultLens<HOST, SUB, SUBLENS> {
// 
// public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>>
// RuleLens<HOST, SUB, SUBLENS> createRuleLens(
// LensSpec<HOST, TYPE>                   lensSpec,
// Function<TYPE,  IRule<SUB>>            readSub,
// WriteLens<TYPE, IRule<SUB>>            writeSub,
// Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
// val readThis  = lensSpec.getRead();
// val writeThis = lensSpec.getWrite();
// val subRead   = (Function<HOST, IRule<SUB>>) LensUtils.createSubRead(readThis,  readSub,             lensSpec.getIsNullSafe());
// val subWrite  = (WriteLens<HOST, IRule<SUB>>)LensUtils.createSubWrite(readThis, writeThis, writeSub, lensSpec.getIsNullSafe());
// val spec      = LensUtils.createLensSpecParameterized(subRead, subWrite, subCreator);
// val lens      = new RuleLens<HOST, SUB, SUBLENS>(spec);
// return lens;
// }
// 
// public static <HOST, SUB, SUBLENS extends AnyLens<HOST, SUB>>
// RuleLens<HOST, SUB, SUBLENS> createRuleLens(
// Function<HOST,  IRule<SUB>>            readSub,
// WriteLens<HOST, IRule<SUB>>            writeSub,
// Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
// val spec = LensUtils.createLensSpecParameterized(readSub, writeSub, subCreator);
// val lens = new RuleLens<HOST, SUB, SUBLENS>(spec);
// return lens;
// }
// 
// private final LensSpecParameterized<HOST, IRule<SUB>, SUB, SUBLENS> spec;
// 
// public RuleLens(LensSpecParameterized<HOST, IRule<SUB>, SUB, SUBLENS> spec) {
// this.spec = spec;
// }
// 
// @SuppressWarnings({ "rawtypes", "unchecked" })
// @Override
// public LensSpecParameterized<HOST, Result<SUB>, SUB, SUBLENS> lensSpecWithSub() {
// return (LensSpecParameterized)spec;
// }
// 
// }
