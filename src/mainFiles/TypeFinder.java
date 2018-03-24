package mainFiles;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class TypeFinder {
	
	  boolean DEBUG = false;		// Prints out additional information for debugging purposes.

	  int  referenceCount = 0;
	  int  declerationCount = 0;
	  boolean containsPackage = false;	// DO NOT CHANGE
	  String javaType = "";
	  String directory = "";
	  public String outputString;
	  
	  
	  public static void main(String[] args) {
		  
		  TypeFinder finder = new TypeFinder();
		  finder.run(args);
		  finder.printAnswer();
	  }
	  
	  public void run(String[] args) {

		  if (args.length != 2 ) throw new IllegalArgumentException("Incorrect number of arguments");
		  directory = args[0];	
		  javaType = args[1];	//Need to use to count which java type you want
		  if (javaType.contains(".")) containsPackage = true;
				
		  try {
			  parseDirectory(directory);
		  } catch (IOException e) {
			  e.printStackTrace();
			  System.exit(0);
		  	}
	
		outputString = javaType + ". Declarations found: " + declerationCount + "; references found: " + referenceCount + ".";
		if (DEBUG) System.out.println(outputString);
	  }
	  
	  public void printAnswer() {
		  if (!DEBUG) System.out.println(outputString);
	  }
	  
	  
	  public void parse(String str) {
		  
		  Map options = JavaCore.getOptions();
		  options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		  options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);

		  ASTParser parser = ASTParser.newParser(AST.JLS3);
		  parser.setCompilerOptions(options);
		  parser.setSource(str.toCharArray());
		  parser.setKind(ASTParser.K_COMPILATION_UNIT);
		  parser.setResolveBindings(true);
		  parser.setBindingsRecovery(true);
		  parser.setEnvironment(null, null, null, true);
		  parser.setUnitName("doesThisMatter.java");
				 
			 
		  final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
		  cu.accept(new ASTVisitor() {
	 
			  
				public boolean visit(TypeDeclaration node) {
					String name = node.getName().getFullyQualifiedName();
					ITypeBinding nodeBinding = node.resolveBinding();
					
					if (containsPackage) {
							if (nodeBinding.getTypeDeclaration() != null) {
								name = nodeBinding.getTypeDeclaration().getQualifiedName();
							} else if (nodeBinding.getPackage() != null) {
								name = nodeBinding.getPackage().getName() + "." + name;
							}
						
					}
						
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " +name);
			

					if (node.getSuperclassType() != null) {
						if (containsPackage) {
							ITypeBinding superNodeBinding = node.getSuperclassType().resolveBinding();
							if (superNodeBinding.getPackage() != null) {
								String superClassName = superNodeBinding.getPackage().getName() + "." + node.getSuperclassType();
								if (javaType.equals(superClassName)) referenceCount++;
							}
						} else {
							if (javaType.equals(node.getSuperclassType().toString())) referenceCount++;
						}
						if (DEBUG) System.out.println("This class extends " + node.getSuperclassType());
					}
	
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						if (containsPackage) {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getQualifiedName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getQualifiedName());
							}
						} else {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getName());
							}
						}
					}

					return super.visit(node); 
				}
				
		
				public boolean visit(VariableDeclarationFragment node) {
					String name;

					if (containsPackage) {
						name = node.resolveBinding().getType().getQualifiedName();
					} else {
						name = node.resolveBinding().getType().getName();

						
					}
			
					if (javaType.equals(name)) referenceCount++;
					if (DEBUG) System.out.println("Variable Reference: " + name);
	
					return super.visit(node);
				}
				
				
				public boolean visit(ImportDeclaration node) {
					String name = node.getName().toString();
					String[] importParts = name.split("\\.");
					String[] typeParts = javaType.split("\\.");
					
						boolean match = true;
						for (int i = 0; i < typeParts.length; i ++) {
							if ((typeParts.length - i > 0 && importParts.length - i > 0)
									&& !(typeParts[typeParts.length - (1 + i)].equals(importParts[importParts.length - (1 + i)]))) 
									{ match = false;}
						}
						if (match) referenceCount++;

					return super.visit(node);
				}
				
					
				public boolean visit(MethodDeclaration node) {
					

					String name;
					IMethodBinding imb = node.resolveBinding();

					
					if (node.isConstructor()) {
						if (containsPackage) {
							name = imb.getDeclaringClass().getQualifiedName();
							if (javaType.equals(name)) referenceCount++;
							if (DEBUG) System.out.println("Constructor Reference: " + name);
						} else {
							if (javaType.equals(node.getName().getFullyQualifiedName())) referenceCount++;
							if (DEBUG) System.out.println("Constructor Reference: " + node.getName().getFullyQualifiedName());
						}
					}
					
				
					

					if (containsPackage) {
						name = imb.getReturnType().getQualifiedName();
						if (javaType.equals(name)) referenceCount ++;
						if (DEBUG) System.out.println("Method Return Type Reference: " + name);
					}
					else {

						name = imb.getReturnType().getName();
						if (javaType.equals(name)) referenceCount ++;
						if (DEBUG) System.out.println("Method Return Type Reference: " + name);

					}
				
					for (Object o : node.parameters()) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
						if (containsPackage) {
							IVariableBinding nodeBinding = svd.resolveBinding();
							name = nodeBinding.getType().getQualifiedName();
							if (javaType.equals(name)) referenceCount++;
							if (DEBUG) System.out.println("Parameter Variable Reference: " + name);					
						} else {
							name = svd.getType().toString();
							if (javaType.equals(name)) referenceCount++;
							if (DEBUG) System.out.println("Parameter Variable Reference: " + name);						
						}
					}
					
					List exceptions = node.thrownExceptions();
					
					for (Object e : exceptions) {
						String exceptionName;
						SimpleName svd = (SimpleName) e;

						if (containsPackage) {
							exceptionName = svd.resolveTypeBinding().getQualifiedName();	
						} else {
							exceptionName = svd.resolveTypeBinding().getName();		
						}
						if (javaType.equals(exceptionName)) referenceCount ++;			
						if (DEBUG) System.out.println("Exeption Reference Reference: " + name);
					}
					

					return super.visit(node);
				
				
				}
				
						
				public boolean visit(ClassInstanceCreation node) {
					String name;
				
					if (containsPackage) {
						name = node.resolveTypeBinding().getQualifiedName();			
					} else {
						name = node.getType().toString();
					}

					if (javaType.equals(name)) referenceCount++;
					if (DEBUG) System.out.println("Instance Variable Reference: " + name);
					
					return false; // do not continue 
			}

				
				public boolean visit(AnnotationTypeDeclaration node) {
					String name;
					
					if (containsPackage) {
						name = node.resolveBinding().getQualifiedName();		
					} else {
						name = node.getName().getFullyQualifiedName();
					}
					
					if (javaType.equals(name)) declerationCount++;	
					if (DEBUG) System.out.println("Declaration: " + name);
					
					return false; // do not continue 
				}
				
				
				public boolean visit(EnumDeclaration node) {
					String name;
					if (containsPackage) {
						name = node.resolveBinding().getQualifiedName();		
					} else {
						name = node.getName().getFullyQualifiedName();
					}
					
					if (javaType.equals(name)) declerationCount++;
					if (DEBUG) System.out.println("Declaration: " + name);

					
					ITypeBinding nodeBinding = node.resolveBinding();
					if (nodeBinding.getInterfaces() != null) {
						ITypeBinding[] interfaces = nodeBinding.getInterfaces();
						if (containsPackage) {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getQualifiedName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getQualifiedName());
							}
						} else {
							for (ITypeBinding i : interfaces) {
								if (javaType.equals(i.getName())) referenceCount++;
								if (DEBUG) System.out.println("implements Reference: " + i.getName());
							}
						}
					}

					return false; // do not continue 
				}
				
				
				public boolean visit(CatchClause node) {
					String name;
					ITypeBinding nodeBinding = node.getException().getType().resolveBinding();
					
					if (nodeBinding != null) {
						if (containsPackage) {
							name = nodeBinding.getQualifiedName();
							if (javaType.equals(name)) referenceCount++;
						} else {
							name = nodeBinding.getName();
							if (javaType.equals(name)) referenceCount++;
						}
						if (DEBUG) System.out.println("Reference: "+ name);
					}
					return false;
				}
				
				

			});
		}
	  
	  
  
	  public String readFile(String filePath) throws IOException {
			StringBuilder fileData = new StringBuilder(1000);
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			int numRead = 0; 
			char[] buffer = new char[10];

			while ((numRead = reader.read(buffer)) != -1) {
				String readData = String.valueOf(buffer, 0, numRead);
				fileData.append(readData);
				buffer = new char[1024];
			}
	 
			reader.close();
			return  fileData.toString();  
	}
	
	  
	  
	  public void parseDirectory(String filePath) throws IOException {

			File directory = new File(filePath);	  
			File[] files = directory.listFiles(); 
			
			
			if (files == null) throw new NullPointerException("Directory '" + directory +"' does not exist.");

			  for (File i: files) {
				  String currentFilePath = i.getAbsolutePath();
				  
				  if (i.getName().endsWith(".java")) parse(readFile(currentFilePath));
				  //if (i.isDirectory()) parseDirectory(i.getAbsolutePath());
			  	}	  
	  	}
	  


}
