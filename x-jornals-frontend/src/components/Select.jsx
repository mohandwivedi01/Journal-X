import React, {useId} from "react";

function Select({
    options,
    label,
    className,
    ...props
}, ref) {
    return (
        <div className="w-full">
            {label && <label htmlFor={id} className=''>
                <select {...props} id={id} ref={ref} className={`px-3 py-2 rounded;lg bg-white text-black outline-none focus:bg-slate-50 duration-200 border border-slate-200 w-full ${className}`}></select>
                {options?.map((option) => (
                    <option key={option} value={option}>
                        {option}
                    </option>
                ))}
            </label>}
        </div>
    )
}

export default React.forwardRef(Select)