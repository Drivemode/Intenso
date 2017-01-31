# Intenso

Small set of utility methods for reflection API in Java.

## Usage

This might be useful for testing purpose.

### Invoke a hidden instance method

```
// no args
Object target = // ...
Object result = Intenso.invoke(target, "methodName");
```

```
Object target = // ...
Object result = Intenso.invoke(target, "methodName", new Class[] {String.class}, new Object[] {"foo"});
```

### Invoke a hidden static method

```
// no args
Object result = Intenso.invoke(Target.class, "methodName");
```

```
Object result = Intenso.invoke(Target.class, "methodName", new Class[] {String.class}, new Object[] {"foo"});
```

### Accessing a hidden field

```
Object target = // ...
Object value = Intenso.get(target, "fieldName");
Intenso.set(target, "fieldName", "something new!")
```

### Overwriting a value of a final field

```
Intenso.overwrite(Target.class, "fieldName", "value");
```

## Download

TBA

## License

```
Copyright (C) 2017 Drivemode, Inc. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
```
